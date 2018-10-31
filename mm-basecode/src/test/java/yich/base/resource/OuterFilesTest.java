package yich.base.resource;

import org.junit.Test;

import static org.junit.Assert.*;

public class OuterFilesTest extends OuterFiles{
    //public static Item TEST_FILE_1 = item("_config/logging.properties");
    public static Item TEST_FILE_2 =
            item("D:\\Project\\IntelliJ IDEA\\marshmallow\\_config\\logging.properties");

    @Test
    public void getStr() {
        //System.out.println(TEST_FILE_1.str());

        System.out.println(TEST_FILE_2.str());
    }

    @Test
    public void getIS() {
    }

    @Test
    public void item() {

    }
}