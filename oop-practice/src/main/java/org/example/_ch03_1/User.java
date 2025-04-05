package org.example._ch03_1;

public class User {
    private String password;

    public void initPassword() {
        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        String randomPassword = randomPasswordGenerator.generatePassword();

        if (randomPassword.length() >= 8 && randomPassword.length() <= 12)
            this.password = randomPassword;
    }

    public String getPassword() {
        return password;
    }
}
