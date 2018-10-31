package yich.base.dbc;

public class OtherPrecTests {
    public int getLength(String target){
        Require.argumentNotNull(target);
        //Require.argumentNotNull(target, "String target");

        return target.length();
    }

    private int month = 1;
    public void setMonth(int month){
//        Require.argumentAll(new boolean[]{month > 0, month < 13}, month, new String[]{"month > 0", "month < 13"});
        Require.argumentAll(G.a(month > 0, month < 13), month, G.a("month > 0", "month < 13"));
//        Require.argument(month > 0, month, "month > 0");
//        Require.argument(month < 13, month, "month < 13");
//        if (month <= 0 || month >12)
//            throw new IllegalArgumentException("Illegal Argument");

        this.month = month;
    }

    public float divide(float left, float right){
        Require.argument(right != 0, right, "right != 0");

        return left / right;
    }

    public void process(byte[] bytes){
        Require.argumentNotNull(bytes, "byte[] bytes");
//        Require.argumentAny(new boolean[]{bytes.length == 2, bytes.length == 4},
//                bytes.length, "%s bytes input", "2 bytes or 4 bytes only");
//        Require.argumentAny(G.a(bytes.length == 2, bytes.length == 4),
//                bytes.length, "%s bytes input", "2 bytes or 4 bytes only");
        Require.argument(bytes.length == 2 || bytes.length == 4,
                bytes.length, "%s bytes input", "2 bytes or 4 bytes only");

        String result = new String(bytes);
        System.out.println(result);
    }


    @org.junit.Test
    public void test() {

//        new OtherPrecTests().getLength(null);

//        long before = System.currentTimeMillis();
//        OtherPrecTests t = new OtherPrecTests();
//        for(int i = 0; i < 1000000000; i++){
//            t.setMonth((i % 12) + 1);
//        }
//        System.out.println(System.currentTimeMillis() - before);

//        new OtherPrecTests().divide(100, 0);
//        new OtherPrecTests().process(new byte[3]);

    }

}
