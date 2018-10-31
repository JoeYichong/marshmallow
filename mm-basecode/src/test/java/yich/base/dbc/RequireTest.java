package yich.base.dbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RequireTest {
    /**
     * Random values used for testing are automatically generated.
     * @see Require#valueInArray(Object[], int)
     * */
    @org.junit.Test
    public void valueInArray(){
        RequireTestAssitant.getInstance().testValueInArray_random(10);
    }

    /**
     * @see Require#argumentNotNullAndNotEmpty(Object[], String)
     * */
    @org.junit.Test
    public void argumentNotNullAndNotEmpty_1(){
        // a null value passed into the method as an array
        try{
            Require.argumentNotNullAndNotEmpty((Object[]) null, "Array arr");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: Array arr} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // an empty array passed into the method
        try{
            Require.argumentNotNullAndNotEmpty(new Object[0], "Array arr");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Array{@sig: Array arr} is Empty",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // null. null
        try{
            Require.argumentNotNullAndNotEmpty((Object[]) null, null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // "", new Object[0]
        try{
            Require.argumentNotNullAndNotEmpty(new Object[0], "");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Array{@sig: [-]} is Empty",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
    }

    /**
     * @see Require#argumentNotNullAndNotEmpty(String, String)
     * */
    @org.junit.Test
    public void argumentNotNullAndNotEmpty_2(){
        // a null value passed into the method as an string
        try{
            Require.argumentNotNullAndNotEmpty((String) null, "String str");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: String str} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // an empty string passed into the method
        try{
            Require.argumentNotNullAndNotEmpty("", "String str");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: String{@sig: String str} is Empty",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // null. null
        try{
            Require.argumentNotNullAndNotEmpty((String) null, null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // "", ""
        try{
            Require.argumentNotNullAndNotEmpty("", "");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: String{@sig: [-]} is Empty",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
    }

    /**
     * @see Require#argumentNotNull(Object)
     * */
    @org.junit.Test
    public void argNotNull_1() {
        // null argument
        try{
            Require.argumentNotNull(null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // non-null argument
        try{
            Require.argumentNotNull("");
        }catch (IllegalArgumentException e){
            fail("IllegalArgumentException isn't supposed to be thrown");
        }

        RequireTestAssitant.getInstance().testAssertNotNull_1_random(10);
    }

    /**
     * @see Require#argumentNotNull(Object, String)
     * */
    @org.junit.Test
    public void argNotNull_2() {
        // null argument
        try{
            Require.argumentNotNull(null, "Object obj");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: Object obj} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // non-null argument
        try{
            Require.argumentNotNull("", "Object obj");
        }catch (IllegalArgumentException e){
            fail("IllegalArgumentException isn't supposed to be thrown");
        }

        // null argument, param not available ("" empty string)
        try{
            Require.argumentNotNull(null, "");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // null argument, param not available (null value)
        try{
            Require.argumentNotNull(null, null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }


    /**
     * @see Require#argumentsNotNull(Object[], String[])
     * */
    @org.junit.Test
    public void argNotNull_4() {
        // a batch of objects to be checked, the first one is null
        try{
            Require.argumentsNotNull(new Object[]{null, "123", new Object(), false},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: String s1} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // a batch of objects to be checked, the last one is null
        try{
            Require.argumentsNotNull(new Object[]{"", "123", new Object(), null},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: Boolean b} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // a batch of objects to be checked, the 3rd one is null
        try{
            Require.argumentsNotNull(new Object[]{"", "123", null, true},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: Object o} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // a batch of objects without null value
        try{
            Require.argumentsNotNull(new Object[]{"", "123", 100, true},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
        }catch (IllegalArgumentException e){
            fail("IllegalArgumentException isn't supposed to be thrown");
        }
        // a batch of objects to be checked, the 3rd one is null and its corresponding sig is also null
        try{
            Require.argumentsNotNull(new Object[]{"", "123", null, true},
                    new String[]{"String s1", "String s2", null, "Boolean b"});
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
    }

    /**
     * @see Require#argument(boolean, Object, String)
     * */
    @org.junit.Test
    public void argument_1() {
        try{
            int arg = 0;
            Require.argument(arg > 0, arg, "arg > 0");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@val: 0} doesn't meet the {@prec: arg > 0}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int arg = 1;
            Require.argument(arg > 0, arg, "arg > 0");
        }catch (IllegalArgumentException e){
            fail("An IllegalArgumentException isn't supposed to be thrown");
        }
        try{
            int arg = 0;
            Require.argument(arg > 0, null, null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e) {
            assertEquals("[Problem]: {@val: [-]} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
    }

    /**
     * @see Require#argument(boolean, Object, String, String)
     * */
    @org.junit.Test
    public void argument_2() {
        try{
            Object[] objs = new Object[10];
            Require.argument(objs.length > 10, objs.length, "The length of object array argument is %s","objs.length > 10");
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@actual: The length of object array argument is 10} doesn't meet the {@prec: objs.length > 10}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            Object[] objs = new Object[11];
            Require.argument(objs.length > 10, objs.length, "The length of object array argument is %s","objs.length > 10");
        }catch (IllegalArgumentException e){
            fail("IllegalArgumentException isn't supposed to be thrown");
        }
        try{
            Object[] objs = new Object[10];
            Require.argument(objs.length > 10, null, "The length of object array argument is %s",null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e) {
            assertEquals("[Problem]: {@actual: The length of object array argument is [-]} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            Object[] objs = new Object[10];
            Require.argument(objs.length > 10, null, null,null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e) {
            assertEquals("[Problem]: {@actual: [-]} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            Object[] objs = new Object[10];
            Require.argument(objs.length > 10, 10, null,null);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e) {
            assertEquals("[Problem]: {@actual: 10} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
    }

    /**
     * @see Require#argumentAll(boolean[], Object, String[])
     * */
    @org.junit.Test
    public void argumentAll_1() {

        try{
            int val = 10;
            boolean[] prec_expr = {val > 0, val != 50, val < 100};
            String[] precs = new String[]{"val > 0", "val != 50", "val < 100"};

            Require.argumentAll(prec_expr, val, precs);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            fail("IllegalArgumentException isn't supposed to be thrown");
        }
        try{
            int val = 0;
            boolean[] prec_expr = {val > 0, val != 50, val < 100};
            String[] precs = new String[]{"val > 0", "val != 50", "val < 100"};
            Require.argumentAll(prec_expr, val, precs);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@val: 0} doesn't meet the {@prec: val > 0}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int val = 50;
            boolean[] prec_expr = {val > 0, val != 50, val < 100};
            String[] precs = new String[]{"val > 0", "val != 50", "val < 100"};

            Require.argumentAll(prec_expr, val, precs);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@val: 50} doesn't meet the {@prec: val != 50}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int val = 100;
            boolean[] prec_expr = {val > 0, val != 50, val < 100};
            String[] precs = new String[]{"val > 0", "val != 50", "val < 100"};

            Require.argumentAll(prec_expr, val, precs);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@val: 100} doesn't meet the {@prec: val < 100}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }

    /**
     * @see Require#argumentAll(boolean[], Object, String, String[])
     * */
    @org.junit.Test
    public void argumentAll_2() {
        try{
            int[] arr = new int[]{10, 20, 30, 40};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 10", "arr.length != 3"};
            Require.argumentAll(new boolean[]{arr.length > 0, arr.length < 10, arr.length != 3}, arr.length, desc_templ, prec_strs);
        }catch (IllegalArgumentException e){
            fail("IllegalArgumentException isn't supposed to be thrown");
        }
        try{
            int[] arr = new int[]{};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 10", "arr.length != 3"};
            Require.argumentAll(new boolean[]{arr.length > 0, arr.length < 10, arr.length != 3}, arr.length, desc_templ, prec_strs);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@actual: arr's length is 0} doesn't meet the {@prec: arr.length > 0}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int[] arr = new int[]{10, 20, 30, 40, 50};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 5", "arr.length != 3"};
            Require.argumentAll(new boolean[]{arr.length > 0, arr.length < 5, arr.length != 3}, arr.length, desc_templ, prec_strs);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@actual: arr's length is 5} doesn't meet the {@prec: arr.length < 5}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int[] arr = new int[]{10, 20, 30, 40};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 10", "arr.length != 4"};
            Require.argumentAll(new boolean[]{arr.length > 0, arr.length < 10, arr.length != 4}, arr.length, desc_templ, prec_strs);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("[Problem]: {@actual: arr's length is 4} doesn't meet the {@prec: arr.length != 4}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }



    /**
     * @see Require#argumentWCM(boolean, String)
     * */
    @org.junit.Test
    public void argumentWCM_1() {
        try{
            String msg = "Custom Exception Message";
            Require.argumentWCM(false, msg);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("Custom Exception Message",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

        try{
            String msg = "Custom Exception Message";
            Require.argumentWCM(true, msg);

        }catch (IllegalArgumentException e){
            fail("An IllegalArgumentException isn't supposed to be thrown");
        }

    }

    /**
     * @see Require#argumentWCM(boolean, Object, String)
     * */
    @org.junit.Test
    public void argumentWCM_2() {
        try{
            String msg = "Custom Exception Message with value %s";
            int value = 100;
            Require.argumentWCM(false, 100, msg);
            fail("An IllegalArgumentException is supposed to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("Custom Exception Message with value 100",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

        try{
            String msg = "Custom Exception Message with value %s";
            Require.argumentWCM(true, 100, msg);

        }catch (IllegalArgumentException e){
            fail("An IllegalArgumentException isn't supposed to be thrown");
        }
    }

    /**
     * @see Require#stateNotNull(Object)
     */
    @org.junit.Test
    public void stateNotNull_1() {
        // null argument
        try{
            Require.stateNotNull(null);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // non-null argument
        try{
            Require.stateNotNull("");
        }catch (IllegalStateException e){
            fail("IllegalStateException isn't supposed to be thrown");
        }

    }

    /**
     * @see Require#stateNotNull(Object, String)
     * */
    @org.junit.Test
    public void stateNotNull_2() {
        // null argument
        try{
            Require.stateNotNull(null, "Object obj");
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object{@sig: Object obj} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // non-null argument
        try{
            Require.stateNotNull("", "Object obj");
        }catch (IllegalStateException e){
            fail("IllegalStateException isn't supposed to be thrown");
        }

        // null argument, param not available ("" empty string)
        try{
            Require.stateNotNull(null, "");
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // null argument, param not available (null value)
        try{
            Require.stateNotNull(null, null);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }


    /**
     * @see Require#statesNotNull(Object[], String[])
     * */
    @org.junit.Test
    public void statesNotNull_2() {
        // a batch of objects to be checked, the first one is null
        try{
            Require.statesNotNull(new Object[]{null, "123", new Object(), false},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object{@sig: String s1} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // a batch of objects to be checked, the last one is null
        try{
            Require.statesNotNull(new Object[]{"", "123", new Object(), null},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object{@sig: Boolean b} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // a batch of objects to be checked, the 3rd one is null
        try{
            Require.statesNotNull(new Object[]{"", "123", null, true},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object{@sig: Object o} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        // a batch of objects without null value
        try{
            Require.statesNotNull(new Object[]{"", "123", 100, true},
                    new String[]{"String s1", "String s2", "Object o", "Boolean b"});
        }catch (IllegalStateException e){
            fail("IllegalStateException isn't supposed to be thrown");
        }
        // a batch of objects to be checked, the 3rd one is null and its corresponding sig is also null
        try{
            Require.statesNotNull(new Object[]{"", "123", null, true},
                    new String[]{"String s1", "String s2", null, "Boolean b"});
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: Required Object{@sig: [-]} is NULL",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }

    /**
     * @see Require#state(boolean, Object, String)
     * */
    @org.junit.Test
    public void state_1() {
        try{
            int arg = 0;
            Require.state(arg > 0, arg, "arg > 0");
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@val: 0} doesn't meet the {@prec: arg > 0}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int arg = 1;
            Require.state(arg > 0, arg, "arg > 0");
        }catch (IllegalStateException e){
            fail("An IllegalStateException isn't supposed to be thrown");
        }
        try{
            int arg = 0;
            Require.state(arg > 0, null, null);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e) {
            assertEquals("[Problem]: {@val: [-]} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }

    /**
     * @see Require#state(boolean, Object, String, String)
     * */
    @org.junit.Test
    public void state_2() {
        try{
            Object[] objs = new Object[10];
            Require.state(objs.length > 10, objs.length, "The length of object array State is %s","objs.length > 10");
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@actual: The length of object array State is 10} doesn't meet the {@prec: objs.length > 10}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            Object[] objs = new Object[11];
            Require.state(objs.length > 10, objs.length, "The length of object array State is %s","objs.length > 10");
        }catch (IllegalStateException e){
            fail("IllegalStateException isn't supposed to be thrown");
        }
        try{
            Object[] objs = new Object[10];
            Require.state(objs.length > 10, null, "The length of object array State is %s",null);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e) {
            assertEquals("[Problem]: {@actual: The length of object array State is [-]} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            Object[] objs = new Object[10];
            Require.state(objs.length > 10, null, null,null);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e) {
            assertEquals("[Problem]: {@actual: [-]} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            Object[] objs = new Object[10];
            Require.state(objs.length > 10, 10, null,null);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e) {
            assertEquals("[Problem]: {@actual: 10} doesn't meet the {@prec: [-]}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }

    /**
     * @see Require#stateAll(boolean[], Object, String[])
     * */
    @org.junit.Test
    public void statesAll_1() {
        try{
            int val = 10;
            String[] precs = new String[]{"val > 0", "val != 50", "val < 100"};
            Require.stateAll(new boolean[]{val > 0, val != 50, val < 100}, val, precs);
        }catch (IllegalStateException e){
            fail("IllegalStateException isn't supposed to be thrown");
        }
        try{
            int val = 10;
            String[] precs = new String[]{"val > 10", "val != 50", "val < 100"};
            Require.stateAll(new boolean[]{val > 10, val != 50, val < 100}, val, precs);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@val: 10} doesn't meet the {@prec: val > 10}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int val = 50;
            String[] precs = new String[]{"val > 0", "val != 50", "val < 100"};
            Require.stateAll(new boolean[]{val > 0, val != 50, val < 100}, val, precs);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@val: 50} doesn't meet the {@prec: val != 50}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int val = 100;
            String[] precs = new String[]{"val > 0", "val != 50", "val < 100"};
            Require.stateAll(new boolean[]{val > 0, val != 50, val < 100}, val, precs);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@val: 100} doesn't meet the {@prec: val < 100}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        
    }

    /**
     * @see Require#stateAll(boolean[], Object, String, String[])
     * */
    @org.junit.Test
    public void statesAll_2() {
        try{
            int[] arr = new int[]{10, 20, 30, 40};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 10", "arr.length != 3"};
            Require.stateAll(new boolean[]{arr.length > 0, arr.length < 10, arr.length != 3}, arr.length, desc_templ, prec_strs);
        }catch (IllegalStateException e){
            fail("IllegalStateException isn't supposed to be thrown");
        }
        try{
            int[] arr = new int[]{};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 10", "arr.length != 3"};
            Require.stateAll(new boolean[]{arr.length > 0, arr.length < 10, arr.length != 3}, arr.length, desc_templ, prec_strs);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@actual: arr's length is 0} doesn't meet the {@prec: arr.length > 0}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int[] arr = new int[]{10, 20, 30, 40, 50};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 5", "arr.length != 3"};
            Require.stateAll(new boolean[]{arr.length > 0, arr.length < 5, arr.length != 3}, arr.length, desc_templ, prec_strs);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@actual: arr's length is 5} doesn't meet the {@prec: arr.length < 5}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }
        try{
            int[] arr = new int[]{10, 20, 30, 40};
            String desc_templ = "arr's length is %s";
            String[] prec_strs = new String[]{"arr.length > 0", "arr.length < 10", "arr.length != 4"};
            Require.stateAll(new boolean[]{arr.length > 0, arr.length < 10, arr.length != 4}, arr.length, desc_templ, prec_strs);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("[Problem]: {@actual: arr's length is 4} doesn't meet the {@prec: arr.length != 4}",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

    }

    /**
     * @see Require#stateWCM(boolean, String)
     *
     * */
    @org.junit.Test
    public void stateWCM_1() {
        try{
            String msg = "Custom Exception Message";
            Require.stateWCM(false, msg);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("Custom Exception Message",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

        try{
            String msg = "Custom Exception Message";
            Require.stateWCM(true, msg);

        }catch (IllegalStateException e){
            fail("An IllegalStateException isn't supposed to be thrown");
        }

    }

    /**
     * @see Require#stateWCM(boolean, Object, String)
     *
     * */
    @org.junit.Test
    public void stateWCM_2() {
        try{
            String msg = "Custom Exception Message with value %s";
            int value = 100;
            Require.stateWCM(false, 100, msg);
            fail("An IllegalStateException is supposed to be thrown");
        }catch (IllegalStateException e){
            assertEquals("Custom Exception Message with value 100",
                    e.getMessage().replaceAll("[\r|\n]", ""));
        }

        try{
            String msg = "Custom Exception Message with value %s";
            Require.stateWCM(true, 100, msg);

        }catch (IllegalStateException e){
            fail("An IllegalStateException isn't supposed to be thrown");
        }
    }

}