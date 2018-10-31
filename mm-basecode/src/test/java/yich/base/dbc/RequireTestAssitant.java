package yich.base.dbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RequireTestAssitant {

    /**
     * Use Reflection to gain access a private method in {@code Require}.
     * @see Require#errorMsg(String, Object, String)
     * */
    public static String errorMsg(String template, Object value, String cond) {
        try {
            Method method = Require.class.getDeclaredMethod("errorMsg",
                    String.class, Object.class, String.class);
            method.setAccessible(true);
            return (String) method.invoke(Require.class, template, value, cond);
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException thrown");
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException thrown");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException thrown");
        }
        fail("No Return");
        return null;
    }
    /**
     * Use Reflection to gain access a private method in {@code Require}.
     * @see Require#errorMsg(String, String, Object, String)
     * */
    public static String errorMsg(String msg_templ, String desc_templ, Object value, String cond) {
        try {
            Method method = Require.class.getDeclaredMethod("errorMsg",
                    String.class, String.class, Object.class, String.class);
            method.setAccessible(true);
            return (String) method.invoke(Require.class, msg_templ, desc_templ, value, cond);
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException thrown");
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException thrown");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException thrown");
        }
        fail("No Return");
        return null;
    }
    /**
     * Use Reflection to gain access a private method in {@code Require}.
     * @see Require#nullMsg(String, String)
     * */
    public static String nullMsg(String template, String param) {
        try {
            Method method = Require.class.getDeclaredMethod("nullMsg",
                    String.class, String.class);
            method.setAccessible(true);
            return (String) method.invoke(Require.class, template, param);
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException thrown");
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException thrown");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException thrown");
        }
        fail("No Return");
        return null;
    }

    /*
     * a private method in {@code Require} used by Varargs methods to
     * check that there is at least one argument passed in.
     * @see Require#checkVarargs(Object[])
     * */
//    public static <T> void checkVarargs(T[] arr) {
//    }

    /**
     * Use Reflection to gain access a private method in {@code Require}.
     * @see Require#valueInArray(Object[], int)
     * */
    public static <T> T valueInArray(T[] arr, int index) {
        try {
            Method method = Require.class.getDeclaredMethod("valueInArray",
                    Object[].class, int.class);
            method.setAccessible(true);
            return (T) method.invoke(Require.class, arr, index);
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException thrown");
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException thrown");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException thrown");
        }
        fail("No Return");
        return null;
    }

    private static RequireTestAssitant instance = null;
    private Random rand = null;
    /**
     * a private constructor to prevent instantiation using 'new' keyword
     * */
    private RequireTestAssitant(){
        rand = new Random();
    }

    /**
     * get an instance of {@code RequireTestAssitant} class
     * using singleton pattern and lazy instantiation strategy
     * */
    public static RequireTestAssitant getInstance(){
        if (instance == null){
            instance = new RequireTestAssitant();
        }
        return instance;
    }

    /**
     * @
     * */
    public String[] strs = {"Object o", "String str", "Boolean bo", "Byte by",
            "Short sh", "Integer i", "Long l", "Float f", "Double d"};
    public Object[] vals = {new Object(), "a String", true, (byte) 8,
            (short) 1, 12, 100L, 12.09f, 12.008d};
    public Object[][] arrs = {strs, vals, null, new String[]{}, null, new Object[]{}};
    public String[] arrs_desc = {"strs", "vals", "null", "new String[]{}", "null", "new Object[]{}"};

    /**
     * print a line of index & value pairs in an array
     * @param num line number.
     * @param index array index
     * @param val value in an array whose position is indicated by the {@code index} parameter
     * */
    public void printIndexAndValue(int num, int index, Object val){
        System.out.println((num + 1) + ". Index = " + index + ", Value = " + String.valueOf(val));
    }

    /**
     * @see RequireTest#valueInArray()
     * @param times used to indicate how many times random value test will run.
     * */
    public void testValueInArray_random(int times){
        System.out.println(" - testValueInArray_random() start: ");
        int index = 0;
        Object val = null;
        for(int i = 0; i < times; i++){
            try{
                val = RequireTestAssitant.valueInArray(vals, (index = rand.nextInt(18)));
                printIndexAndValue(i, index, val);
                if(index > 8 && val != null)
                    fail("Value should be null if index out of boundary");
            }catch (IndexOutOfBoundsException e){
                fail("IndexOutOfBoundsException is not supposed to be thrown");
            }
        }
        System.out.println(" - testValueInArray_random() end.");
    }



    /**
     * @see RequireTest#argNotNull_1()
     * @param times used to indicate how many times random value test will run.
     * */
    public void testAssertNotNull_1_random(int times){
        Object obj = null;
        int index = 0;
        System.out.println(" - RequireTestAssitant testAssertNotNull_1_random start: ");
        for(int i = 0; i < times; i++){
            //obj = ((index = rand.nextInt(18)) < 9) ? vals[index] : null;
            obj = RequireTestAssitant.valueInArray(vals, index = rand.nextInt(18));
            System.out.println((i + 1) + ". Index = " + index + " , value = " + String.valueOf(obj));
            try{
                Require.argumentNotNull(obj);
                if (obj == null)
                    fail("obj == null, fail to throw an IllegalArgumentException");
            }catch (IllegalArgumentException e){
                if (obj == null){
                    System.out.println("IllegalArgumentException caught, assertEquals(..) called");
                    assertEquals("[Problem]: Required Object is NULL",
                            e.getMessage().replaceAll("[\r|\n]", ""));
                }
                else fail("obj != null, IllegalArgumentException is not supposed to be thrown");
            }
        }
        System.out.println(" - RequireTestAssitant testAssertNotNull_1_random end.");
    }


}
