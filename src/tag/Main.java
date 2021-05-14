package tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	//声明标签列表变量
    static List<Tag> tagtag;
    //添加标签
    public static void addtag(String id,List<String> tagcon){
        Tag tagtype = new Tag();
        tagtype.setTagID(id);
        tagtype.setTagContent(tagcon);
        tagtag.add(tagtype);
    }
    //打印标签
    public static void showtag(List<Tag> tagtag){
        for(int i = 0 ; i<tagtag.size() ; i++){
            System.out.println(tagtag.get(i).getTagID());
            for(int j = 0 ; j <tagtag.get(i).getTagContent().size() ; j++){
                System.out.println("  "+tagtag.get(i).getTagContent().get(j));
            }
        }
    }
    public static void init(){
        tagtag=new ArrayList<>();
        List<String> exp1= Arrays.asList("男","女");
        addtag("性别",exp1);
    }
    public static void main(String args[]){
        init();
        showtag(tagtag);
        System.out.print("\n");
        List<String> exp2= Arrays.asList("13","17");
        addtag("年龄",exp2);
        showtag(tagtag);
    }
}
