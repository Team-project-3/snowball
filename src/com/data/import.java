@Service
public class FileImportUtil {
    private static final Logger log = LoggerFactory.getLogger(FilePortUtil.class);
 
    @Autowired
    private IndexService indexService;
 
    /**
     * 导入功能
     *
     * @param multipartFile
     * @return
     */
    public int fileImport(MultipartFile multipartFile) {
        File file = null;
        Workbook workbook = null;
        int totalNum = 0;
        
        String path = FilePortUtil.class.getClassLoader().getResource("/").getPath();
        
        path = path.substring(0, path.indexOf("File-name") + "File-name".length()) + "/" + multipartFile.getOriginalFilename();
        file = new File(path
        try {
            /*把文件流copy读取到文件中*/
            FileCopyUtils.copy(multipartFile.getBytes(), file);
            workbook = WorkbookFactory.create(new FileInputStream(file));
            List<Movie> list = new ArrayList<>();
        
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
               
                if (sheet.getLastRowNum() > 0) {
                    totalNum += sheet.getLastRowNum() + 1;
                }
              
                for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    /*解析列，下标从0开始*/
                    Cell cell2 = row.getCell(2);
                    Cell cell3 = row.getCell(3);
                    if (cell2 == null || cell3 == null) {
                        continue;
                    }
                    String name = this.getCellValue(cell2);
                    String original = this.getCellValue(cell3);
 
                    /*我这里省略了很多数据清洗、校验的代码*/
                    
                    Movie movie = new Movie();
                    movie.setName(name);
                    movie.setOriginal(original);
                    list.add(movie);
                }
                /*持久化：批量新增*/
                indexService.insertBatch(list);
            }
            /*解析完删除此路径下的文件*/
            file.delete();
            return totalNum;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入功能公用类异常exception={}", e);
        }
        return totalNum;
    }
 
    public String getCellValue(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            Double d = cell.getNumericCellValue();
            return String.valueOf(d.intValue());
        }
        return String.valueOf(cell.getStringCellValue());
    }
    
}
