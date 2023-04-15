import CurrentMarketPricesPackage.CurrentMarketPrices;
import RegisterScreenPackage.RegisterScreen;
import LoginScreenPackage.LoginScreen;
import UserProfileScreenPackage.UserProfileScreen;
import BalanceScreenPackage.BalanceScreen;


public class Main {
    public static void main(String[] args) {
        CurrentMarketPrices currentMarketPrices = new CurrentMarketPrices();
        RegisterScreen registerScreen = new RegisterScreen();
        LoginScreen loginScreen = new LoginScreen();
        UserProfileScreen userProfileScreen = new UserProfileScreen();
        BalanceScreen balanceScreen = new BalanceScreen();
    }
}