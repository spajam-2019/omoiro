
public class Main {
    public static void main(String[] args) throws Exception {
      HttpClient h= new HttpClient();
      h.GetOmoiros();
      ReqOmoiro reqOmoiro= new ReqOmoiro();
      reqOmoiro.text="java";
      reqOmoiro.user_id=1;
      reqOmoiro.image_urls=new String[]{"aaaa.png"};
      reqOmoiro.color=new Color();
      reqOmoiro.color.code="#FFFFEE";
      reqOmoiro.color.furigana="あああ";
      reqOmoiro.color.name="JAVA";
      h.CreateOmoiro(reqOmoiro);

      ReqEmoPush reqEmoPush= new ReqEmoPush();
      reqEmoPush.user_id=1;
      reqEmoPush.omoiro_id="0";
      System.out.println(h.AddEmoPush(reqEmoPush));
    }
}
