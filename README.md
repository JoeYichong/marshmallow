# Marshmallow
Marshmallow is a common library that simplifies Java programming.

## Compile
[OpenJDK 11](https://openjdk.java.net/projects/jdk/11/) is required to compile this project.

## Example

Suppose that you have a file called `config.properties`:
```
user = test
password = 123456
```

You can use the following code to to access it: 
```java
import yich.base.resource.PropertyFiles;

public class Config extends PropertyFiles {
    private static Item INFO = item("/_config/config.properties");

    public static String get(String key){
        return INFO.getProperty(key);
    }
}
```
To get a property from the `config.properties`:

```
System.out.println(Config.get("user"));
```
Output:
```
test
```
