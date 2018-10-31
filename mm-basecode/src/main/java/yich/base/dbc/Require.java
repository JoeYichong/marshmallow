/*******************************************************************************
 * Copyright 2018 Joe Yichong
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package yich.base.dbc;

/**
 * Inspired by the concept of 'Design by Contract(DBC)' and coding practice,
 * the methods in <code>Require</code> class are designed primarily for
 * checking the preconditions of calling methods or constructors before
 * their actual operations begin to execute, e.g. as demonstrated below:
 * <pre>
 * {@code
 * public void foo(Bar bar, int size){
 * 	   Require.argumentNotNull("Bar bar", bar);
 *     Require.argument(size, "size > 0", size > 0);
 *
 *     // actual operations of this method
 *     ...
 * }
 * }
 * </pre>
 * <p>
 * Throw an {@code IllegalArgumentException} to indicate that
 * the calling method has been passed an illegal or inappropriate argument,
 * throw an {@code IllegalStateException} to signal that the calling method has been
 * invoked at an illegal or inappropriate time/state for the requested operation.
 * {@code NullPointerException} isn't used here.
 * </p>
 * <p>
 * The common traits of these assert methods' parameter signatures are like this:
 * {@code (boolean expression, actual/reality, expected/precondition)}
 * Customized variable information is inserted into the message templates offered by this class to
 * generate specific and precise exception message.
 * <p>Here we use a notation similar to javadoc tags to highlight these variable information.
 * <p>{@code {@sig ...}} means the signature of a method parameter or a state object etc.
 * <p>{@code {@val ...}} means the value of a method parameter or a state object etc.
 * <p>{@code {@actual ...}} means the actual situation of the argument or state object.
 * <p>{@code {@prec ...}} means the description of preconditions.
 * <p>The scenarios of variable information not being available are tolerated by using
 * string '[-]' to indicate this variable information isn't available.
 * <p>Varargs in the methods of this class should be passed at least one non-null value,
 * empty calling or pass null to the Varargs of these methods may introduce unnecessary performance overhead or
 * cause an exception to be thrown.
 *
 * @author Joe Yichong
 * @version 2.10
 */

public final class Require {
    private static final String Msg_NotNull =
            "\r\n[Problem]: Required Object is NULL";
    private static final String Msg_NotNull_Template =
            "\r\n[Problem]: Required Object{@sig: %s} is NULL";
    private static final String Msg_ArrNotEmpty_Template =
            "\r\n[Problem]: Array{@sig: %s} is Empty";
    private static final String Msg_StrNotEmpty_Template =
            "\r\n[Problem]: String{@sig: %s} is Empty";
    private static final String Msg_Template_v =
            "\r\n[Problem]: {@val: %s} doesn't meet the {@prec: %s}";
    private static final String Msg_Template_d =
            "\r\n[Problem]: {@actual: %s} doesn't meet the {@prec: %s}";
    private static final String Msg_Template_any_v   =
            "\r\n[Problem]: {@val: %s} doesn't meet any of these specified conditions{@prec: %s}";
    private static final String Msg_Template_any_d   =
            "\r\n[Problem]: {@actual: %s} doesn't meet any of these specified conditions{@prec: %s}";

    /**
     * If the object is a string instance or a character instance, wrap it in "" or ''.
     * Otherwise convert it to a string using {@code String.valueOf()}
     * @param o an object to be wrapped as a string
     * */
    private static String wrapString(Object o){
        String result;
        if (o instanceof String)
            result = "\"" + o + "\"";
        else if (o instanceof Character)
            result = "\'" + o + "\'";
        else
            result = String.valueOf(o);
        return result;
    }

    /**
     * A private method used to generate exception messages,
     * {@code null} value and empty string("") are tolerated which indicated by using string '[-]' instead.
     *
     * @param msg_templ a template of exception message into which {@code value} and {@code cond} are inserted
     * @param value a object value, string '[-]' is used to indicate this argument isn't available
     * @param cond a string that describes the preconditions,
     *             string '[-]' is used to indicate this argument isn't available
     * */
    private static String errorMsg(String msg_templ, Object value, String cond) {
        String val = (value == null) ? "[-]" : wrapString(value);
        String prec = (cond == null || "".equals(cond)) ? "[-]" : cond;

        return String.format(msg_templ, val, prec);
    }

    /**
     * A private method used to generate exception messages,
     * {@code null} value and empty string("") are tolerated which indicated by using string '[-]' instead or
     * other default values.
     *
     * @param msg_templ a template of exception message into which {@code desc_templ} and {@code cond} are inserted
     * @param desc_templ a template into which {@code value} is inserted, if not specified use {@code value} instead
     * @param value a object value, string '[-]' is used to indicate this argument isn't available
     * @param cond a string that describes the preconditions,
     *             string '[-]' is used to indicate this argument isn't available
     * */
    private static String errorMsg(String msg_templ, String desc_templ, Object value, String cond) {
        String val = (value == null) ? "[-]" : wrapString(value);
        String desc = (desc_templ == null || "".equals(desc_templ)) ? val : String.format(desc_templ, val);
        String prec = (cond == null || "".equals(cond)) ? "[-]" : cond;

        return String.format(msg_templ, desc, prec);
    }

    /**
     * A private method used to generate custom exception messages,
     * {@code null} value and empty string("") are tolerated which indicated by using string '[-]' instead or
     * other default messages.
     *
     * @param msg_template the custom template of exception message, string '[- Custom Error Message Not Available -]' is
     *                     used to indicate that this argument isn't available
     * @param value a object value to be inserted in the message template,
     *              string '[-]' is used to indicate that this argument isn't available
     *
     * */
    private static String customErrorMsg(String msg_template, Object value){
        String val = (value == null) ? "[-]" : wrapString(value);
        String msg_templ = (msg_template == null || "".equals(msg_template)) ?
                "[- Custom Error Message Not Available -]" : msg_template;
        return String.format(msg_templ, val);
    }

    /**
     * A private method used to generate custom exception messages,
     * default message is used if custom message not available.
     *
     * @param msg the custom message to be checked
     *
     * */
    private static String customErrorMsg(String msg){
        return (msg == null || "".equals(msg)) ?
                "[- Custom Error Message Not Available -]" : msg;
    }

    /**
     * A private method used to generate exception messages,
     * {@code null} value and empty string("") are tolerated which indicated by using string '[-]' instead or
     * other default values.
     *
     * @param msg_templ a template of exception message into which {@code param} is inserted
     * @param param the parameter signature of the argument to be checked,
     *              string '[-]' is used to indicate this argument isn't available
     * */
    private static String nullMsg(String msg_templ, String param) {
        String para_n = (param == null || "".equals(param)) ? "[-]" : param;
        return String.format(msg_templ, para_n);
    }

    /**
     * A safe way to extract a value in an array without worrying about {@code IndexOutOfBoundsException} thrown.
     * A private method used by methods in this class to fetch a value in a specified array.
     * A {@code null} value is returned if index out of boundary or the array provided is a {@code null} value.
     * Note that it could also means that the value in the specified array is {@code null}.
     *
     * @param arr the specified array from which a value to be fetched
     * @param index the index of the value to be fetched
     * */
    private static <T> T valueInArray(T[] arr, int index) {
        if(arr != null && arr.length > index) {
            return arr[index];
        }
        return null;
    }


    /* ************************************************************************************************** */

    /**
     * Asserts that the specified array is not null and empty.
     * If it is it throws an {@link IllegalArgumentException} with the given
     * message.
     *
     * @param <T> the type of the array
     * @param arr the array to be checked
     * @param sig a string representation of the array signature
     * @return the original array
     * */
    public static <T> T[] argumentNotNullAndNotEmpty(T[] arr, String sig) {
        if (arr == null)
            throw new IllegalArgumentException(nullMsg(Msg_NotNull_Template, sig));
        if (arr.length == 0)
            throw new IllegalArgumentException(nullMsg(Msg_ArrNotEmpty_Template, sig));
        return arr;
    }

    /**
     * Asserts that the specified string is not null and empty.
     * If it is it throws an {@link IllegalArgumentException} with the given
     * message.
     *
     * @param str the string to be checked
     * @param sig a string representation of the string argument signature
     * @return the original string
     * */
    public static String argumentNotNullAndNotEmpty(String str, String sig) {
        if (str == null)
            throw new IllegalArgumentException(nullMsg(Msg_NotNull_Template, sig));
        if (str.length() == 0)
            throw new IllegalArgumentException(nullMsg(Msg_StrNotEmpty_Template, sig));
        return str;
    }

    /**
     * Asserts that the specified object reference is not null. If it is it throws an
     * {@link IllegalArgumentException} with the given message.
     *
     * @param val the object reference(as argument) passed to the calling method
     * @return the original object reference
     * @throws IllegalArgumentException if the reference is null
     */
    public static <T> T argumentNotNull(T val) {
        if (val == null)
            throw new IllegalArgumentException(Msg_NotNull);
        return val;
    }

    /**
     * Asserts that the specified object reference is not null. If it is it throws an
     * {@link IllegalArgumentException} with the given message.
     *
     * @param val the object reference(as argument) passed to the calling method
     * @param sig a string that represents the reference's parameter signature
     * @return the original object reference
     * @throws IllegalArgumentException if the reference is null
     * */
    public static <T> T argumentNotNull(T val, String sig) {
        if (val == null)
            throw new IllegalArgumentException(nullMsg(Msg_NotNull_Template, sig));
        return val;
    }

    /**
     * Asserts that a batch of object references are not null. If null reference detected
     * it throws an {@link IllegalArgumentException} with the given message.
     *
     * @param vals the object references(as arguments) passed to the calling method
     * @param sig strings that represent the arguments' parameter signatures
     * @throws IllegalArgumentException if null reference detected
     */
    public static void argumentsNotNull(Object[] vals, String[] sig) {
        //checkVarargs(vals);
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == null) {
                String param = valueInArray(sig, i);
                throw new IllegalArgumentException(nullMsg(Msg_NotNull_Template, param));
            }
        }
    }

    /**
     * Asserts that the argument passed to the calling method meets the preconditions.
     * If it doesn't it throws an {@link IllegalArgumentException} with the given
     * message.
     *
     * @param prec_expr a boolean expression that represents the parameter restrictions
     * @param val an argument to be checked
     * @param prec_str a string that represents the parameter restrictions
     * @return the original object reference value
     * @throws IllegalArgumentException if the argument is not valid
     */
    public static <T> T argument(boolean prec_expr, T val, String prec_str) {
        if (!prec_expr)
            throw new IllegalArgumentException(errorMsg(Msg_Template_v, val, prec_str));
        return val;
    }

    /**
     * Asserts that the argument passed to the calling method meets the preconditions.
     * If it doesn't it throws an {@link IllegalArgumentException} with the given
     * message.
     * @param prec_expr a boolean expression that represents the parameter restrictions
     * @param val a value or an attribute of the argument to be checked
     * @param desc_templ a template that describes the reality of the argument to be checked
     * @param prec_str a string that represents the parameter restrictions
     * @return the original object reference value
     * @throws IllegalArgumentException if the argument is not valid
     */
    public static <T> T argument(boolean prec_expr, T val, String desc_templ, String prec_str) {
        if (!prec_expr)
            throw new IllegalArgumentException(errorMsg(Msg_Template_d, desc_templ, val, prec_str));
        return val;
    }

    /**
     * Asserts that argument passed to the calling method meet the preconditions.
     * If they don't it throws an {@link IllegalArgumentException} with the given message.
     *
     * @param prec_exprs boolean expressions that represent the parameter restrictions
     * @param val the argument to be checked
     * @param prec_strs strings that represent the parameter restrictions
     * @return the original object reference value
     * @throws IllegalArgumentException if invalid argument detected
     */
    public static <T> T argumentAll(boolean[] prec_exprs, T val, String[] prec_strs) {
        //checkVarargs(prec_exprs);
        for (int i = 0; i < prec_exprs.length; i++) {
            if (!prec_exprs[i]) {
                String prec_str = valueInArray(prec_strs, i);
                throw new IllegalArgumentException(errorMsg(Msg_Template_v, val, prec_str));
            }
        }
        return val;
    }

    /**
     * Asserts that the argument passed to the calling method meet the preconditions.
     * If they don't it throws an {@link IllegalArgumentException} with the given message.
     *
     * @param prec_exprs boolean expressions that represent the parameter restrictions
     * @param val the value or attribute of the argument to be checked
     * @param desc_templ the template that describe the reality of the argument to be checked
     * @param prec_strs strings that represent the parameter restrictions
     * @return the original object reference value
     * @throws IllegalArgumentException if invalid argument detected
     */
    public static <T> T argumentAll(boolean[] prec_exprs, T val, String desc_templ, String[] prec_strs) {
        //checkVarargs(prec_exprs);
        for (int i = 0; i < prec_exprs.length; i++) {
            if (!prec_exprs[i]) {
                String prec_str = valueInArray(prec_strs, i);
                throw new IllegalArgumentException(errorMsg(Msg_Template_d, desc_templ, val, prec_str));
            }
        }
        return val;
    }

    /**
     * Asserts that the specified condition is true with custom exception message.
     * 'WCM' in the method name stands for 'with custom message'.
     *
     * @param prec_expr the boolean expressions of the specified conditions
     * @param msg the custom exception message
     * @throws IllegalArgumentException if fails to match the specified condition
     *
     * */
    public static void argumentWCM(boolean prec_expr, String msg){
        if (!prec_expr)
            throw new IllegalArgumentException(customErrorMsg(msg));

    }

    /**
     * Asserts that the specified condition is true with custom exception message.
     * 'WCM' in the method name stands for 'with custom message'.
     *
     * @param prec_expr the boolean expressions of the specified conditions
     * @param val the value to be inserted in the message template
     * @param msg_templ the custom message template
     * @throws IllegalArgumentException if fails to match the specified condition
     *
     * */
    public static <T> T argumentWCM(boolean prec_expr, T val, String msg_templ){
        if (!prec_expr)
            throw new IllegalArgumentException(customErrorMsg(msg_templ, val));
        return val;
    }

    /* ************************************************************************************************** */

    /**
     * Asserts that the specified object reference is not null. If it is it throws an
     * {@link IllegalStateException} with the given message.
     *
     * @param val the reference of a state object
     * @return the original object reference
     * @throws IllegalStateException if the reference is null
     */
    public static <T> T stateNotNull(T val) {
        if (val == null)
            throw new IllegalStateException(Msg_NotNull);
        return val;
    }

    /**
     * Asserts that the specified object reference is not null. If it is it throws an
     * {@link IllegalStateException} with the given message.
     *
     * @param val the reference of a state object
     * @param state_name a string that represents the state object's name
     * @return the original object reference
     * @throws IllegalStateException if the reference is null
     * */
    public static <T> T stateNotNull(T val, String state_name) {
        if (val == null)
            throw new IllegalStateException(nullMsg(Msg_NotNull_Template, state_name));
        return val;
    }

    /**
     * Asserts that a batch of object references are not null. If null reference detected,
     * it throws an {@link IllegalStateException} with the given message.
     *
     * @param vals the object references passed to the calling method
     * @param state_names strings that represent the state objects' names
     * @throws IllegalStateException if null reference detected
     */
    public static void statesNotNull(Object[] vals, String[] state_names) {
        //checkVarargs(vals);
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == null) {
                String state_name = valueInArray(state_names, i);
                throw new IllegalStateException(nullMsg(Msg_NotNull_Template, state_name));
            }
        }
    }

    /**
     * Asserts that the state object meets the preconditions.
     * If it doesn't, it throws an {@link IllegalStateException} with the given message.
     *
     * @param prec_expr a boolean expression that represents the preconditions
     * @param val the state object to be checked
     * @param prec_str a string that represents the preconditions
     * @return the original object reference value
     * @throws IllegalStateException if the state is not valid
     */
    public static <T> T state(boolean prec_expr, T val, String prec_str) {
        if (!prec_expr)
            throw new IllegalStateException(errorMsg(Msg_Template_v, val, prec_str));
        return val;
    }


    /**
     * Asserts that the state object meets the preconditions.
     * If it doesn't, it throws an {@link IllegalStateException} with the given message.
     *
     * @param prec_expr a boolean expression that represents the preconditions
     * @param val a value or an attribute of the state object to be checked
     * @param desc_templ a template that describes the reality of the state object to be checked
     * @param prec_str a string that represents the preconditions
     * @return the original object reference value
     * @throws IllegalStateException if the state is not valid
     */
    public static <T> T state(boolean prec_expr, T val, String desc_templ, String prec_str) {
        if (!prec_expr)
            throw new IllegalStateException(errorMsg(Msg_Template_d, desc_templ, val, prec_str));
        return val;
    }


    /**
     * Asserts that the state object meet the preconditions.
     * If they don't, it throws an {@link IllegalStateException} with the given message.
     *
     * @param prec_exprs the boolean expressions that represent the preconditions
     * @param val the state object to be checked
     * @param prec_strs the strings that represent the preconditions
     * @return the original object reference value
     * @throws IllegalStateException if invalid state detected
     */
    public static <T> T stateAll(boolean[] prec_exprs, T val, String[] prec_strs) {
        //checkVarargs(prec_exprs);
        for (int i = 0; i < prec_exprs.length; i++) {
            if (!prec_exprs[i]) {
                String prec_str = valueInArray(prec_strs, i);
                throw new IllegalStateException(errorMsg(Msg_Template_v, val, prec_str));
            }
        }
        return val;
    }

    /**
     * Asserts that the state object meet the preconditions.
     * If they don't, it throws an {@link IllegalStateException} with the given message.
     *
     * @param prec_exprs the boolean expressions that represent the preconditions
     * @param val the value or attribute of the state objects to be checked
     * @param desc_templ the template that describe the reality of the state objects to be checked
     * @param prec_strs the strings that represent the preconditions
     * @return the original object reference value
     * @throws IllegalStateException if invalid state detected
     */
    public static <T> T stateAll(boolean[] prec_exprs, T val, String desc_templ, String[] prec_strs) {
        //checkVarargs(prec_exprs);
        for (int i = 0; i < prec_exprs.length; i++) {
            if (!prec_exprs[i]) {
                String prec_str = valueInArray(prec_strs, i);
                throw new IllegalStateException(errorMsg(Msg_Template_d, desc_templ, val, prec_str));
            }
        }
        return val;
    }

    /**
     * Asserts that the specified conditions is true with custom exception message.
     * 'WCM' in the method name stands for 'with custom message'.
     *
     * @param prec_expr the boolean expressions of the specified conditions
     * @param msg the custom exception message
     * @throws IllegalStateException if fails to match the specified condition
     *
     * */
    public static void stateWCM(boolean prec_expr, String msg){
        if (!prec_expr)
            throw new IllegalStateException(customErrorMsg(msg));

    }

    /**
     * Asserts that the specified conditions is true with custom exception message.
     * 'WCM' in the method name stands for 'with custom message'.
     *
     * @param prec_expr the boolean expressions of the specified conditions
     * @param val the value to be inserted in the message template
     * @param msg_templ the custom message template
     * @return the original object reference value
     * @throws IllegalStateException if fails to match the specified condition
     *
     * */
    public static <T> T stateWCM(boolean prec_expr, T val, String msg_templ){
        if (!prec_expr)
            throw new IllegalStateException(customErrorMsg(msg_templ, val));
        return val;
    }

}
