package LoginTePage;

import utils.ConfigReader;

public class Example {
    public static void main(String[] args) {
        String url = ConfigReader.getProperty("url");
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
    }
}
