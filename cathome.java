import java.util.*;
import java.util.ArrayList;


    //父猫类
    class cat
    {
        String name;
        int age;
        double price;
        void tostring()
        {

        }
        public cat(String name,int age,double price){
            this.age=age;
            this.name=name;
            this.price=price;
        }

        void copy(cat a){
            this.price=a.price;
            this.name=a.name;
            this.age=a.age;
        }

    }
    //橘猫类
    class OrangeCat extends cat
    {
        boolean isFat;
        public OrangeCat(String name,int age)
        {
            super(name,age,200);
        }
    }
    //黑猫类
    class BlackCat extends cat{

        public BlackCat(String name,int age)
        {
            super(name,age,350);
        }
    }
    //不喜欢的猫类
    class WhiteCat extends cat{
        public WhiteCat(String name,int age){
            super(name,age,100);
        }

    }
    //到店时间
    class Localdate{
        public Localdate(int year,int month,int day,int hour,int minute,int second){

            this.year=year;
            this.day=day;
            this.month=month;
            this.hour=hour;
            this.minute=minute;
            this.secend=second;
        }
        //时间参数
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int secend;
    }
    //顾客类
    class Customer extends Localdate
    {
        String Customername;
        int ruatimes;

        public Customer(String Custmername,int ruatimes,int year,int month,int day,int hour,int minute,int second){
            super(year,month,day,hour,minute,second);
            this.Customername=Custmername;
            this.ruatimes=ruatimes;


        }
        //输出顾客信息
        @Override
        public String toString(){
            //System.out.println("顾客的名字"+this.Customername);
            //System.out.println("顾客的rua猫次数"+this.ruatimes);
            //System.out.println("顾客的到店时间"+super.year+"/"+super.month+"/"+super.day+"  "+super.hour+":"+super.minute+":"+super.secend);
            return "顾客的名字"+this.Customername+"\n"+"顾客的rua猫次数"+this.ruatimes+"\n"+"顾客的到店时间"+super.year+"/"+super.month+"/"+super.day+"  "+super.hour+":"+super.minute+":"+super.secend;
        }

    }
    //猫咖接口
    interface Catcafe{
        void Buy(cat a);
        void  CareCustrom(Customer thegay);
        void StopWorking();

    }
    //无猫可rua异常类
    class CatNotFoundException extends RuntimeException{
        public CatNotFoundException() {
            super();
        }
        @Override
        public String toString() {
            return "无猫可rua";
        }
    }
    //余额不足异常类
    class InsufficientBalanceException extends RuntimeException{
        public InsufficientBalanceException() {
            super();
        }
        @Override
        public String toString() {
            return "余额不足";
        }
    }
    //我的猫咖类
    class MyCatCafe implements Catcafe{
        //构造函数
        MyCatCafe(double restMoney,ArrayList<cat> CateName ){
            this.CatName=CateName;

            this.RestMoney=restMoney;
        }
        //歇业变量
        boolean isRest=false;
        //余额
        public double RestMoney;
        //未rua猫list
        ArrayList<cat>CatName=new ArrayList<cat>();

        //在rua猫list
        ArrayList<cat>CatNameing=new ArrayList<cat>(1);

        //来rua猫客户名单
        ArrayList<Customer>CustomerNames=new ArrayList<>();
        //rua猫次数总和
        int ALLruatimes;
        //购买猫函数
        @Override
        public void Buy(cat catone) {

            if(this.RestMoney>catone.price){
                this.RestMoney-=catone.price;
                this.CatName.add(catone);
            }
            else{
                try{
                    throw new InsufficientBalanceException();
                }catch(InsufficientBalanceException e){
                    System.out.println(e.toString());
                }
            }

        }
        //招待顾客函数
        @Override
        public void CareCustrom(Customer thegay)
        {
            //无猫可rua异常
            if(CatName==null)
            {
                try
                {
                    throw new CatNotFoundException();
                }catch(CatNotFoundException e)
                {
                    System.out.print("没有猫了");
                }
            }
            //有猫可rua
            else{



                    int a=CatName.size();

                    Random ran=new Random(a);
                    int x=ran.nextInt(a);                  //得随机数

                    cat z=new cat(CatName.get(x).name,CatName.get(x).age,CatName.get(x).price);
                    //System.out.println("here");
                    CatNameing.add(z);                     //在rua猫类增加
                    CatName.remove(x);                     //未rua猫类删除
                    CustomerNames.add(thegay);             //顾客列表类增加
                    ALLruatimes+=thegay.ruatimes;          //得所有顾客rua猫次数之和

                }
            }

        //歇业函数
        @Override
        public void StopWorking(){
            if(!this.isRest)
                this.isRest=true;                         //修改歇业参数
            System.out.print(this.ALLruatimes*15);        //输出收入
        }
    }


    //测试
    class test{
        public static void main(String[] args){
            cat one=new cat("one",5,5);
            ArrayList<cat>a=new ArrayList<cat>();
            a.add(one);
            //System.out.println("here");

            cat cattwo=new cat("sadf",25,35);
            MyCatCafe MyCatCafeone=new MyCatCafe(20,a);
            MyCatCafeone.Buy(cattwo);
            Customer guke=new Customer("guke",2,2021,11,4,0,0,0);
            MyCatCafeone.CareCustrom(guke);
            MyCatCafeone.StopWorking();
        }
    }

















