package tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	//������ǩ�б����
    static List<Tag> tagtag;
    //��ӱ�ǩ
    public static void addtag(String id,List<String> tagcon){
        Tag tagtype = new Tag();
        tagtype.setTagID(id);
        tagtype.setTagContent(tagcon);
        tagtag.add(tagtype);
    }
    //��ӡ��ǩ
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
        List<String> exp1= Arrays.asList("��","Ů");
        addtag("�Ա�",exp1);
    }
    public static void main(String args[]){
        init();
        showtag(tagtag);
        System.out.print("\n");
        List<String> exp2= Arrays.asList("13","17");
        addtag("����",exp2);
        showtag(tagtag);
    }
}
