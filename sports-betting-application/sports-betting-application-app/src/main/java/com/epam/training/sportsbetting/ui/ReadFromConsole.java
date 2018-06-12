package com.epam.training.sportsbetting.ui;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.SportBettingService;

public class ReadFromConsole {
    
    @Autowired
    private MessageSource messageSource;
    
    @Value("${locale.default}")
    private Locale locale;

    public final IO io;

    private final SportBettingService sbService;
    private final SportBettingDatabase sbDatabase;

    public ReadFromConsole(SportBettingService sbService, SportBettingDatabase sbDatabase, IO io) {
        super();
        this.sbService = sbService;
        this.sbDatabase = sbDatabase;
        this.io = io;
    }

    public void readPlayerDataFromConsole() {

        Player player = sbDatabase.getPlayer();
        readName(player);
        readAccountNumber(player);
        readBalance(player);
        readCurrency(player);
        readDateOfBirth(player);

    }

    private void readDateOfBirth(Player player) {
        String enteredData = null;
        boolean dataIsValid = false;
        do {
//            io.println(" When were you born? eg.:1990-02-03");
            io.print(messageSource.getMessage("when_born", null, locale));
            try {
                if (!(enteredData = io.readOneLine()).equals("")) {
                    player.setBirthDate(sbService.stringToDate(enteredData));
                    dataIsValid = true;
                }
            } catch (Exception e) {
//                io.println(enteredData + " is not a valid date");
                io.print(messageSource.getMessage("is_not_valid", new Object[] {enteredData}, locale));
            }
        } while (!dataIsValid);

    }

    private void readCurrency(Player player) {
        boolean dataIsValid = false;
        String enteredData;
        do {
//            io.println(" What is your currency? (HUF, EUR or USD)");
            io.print(messageSource.getMessage("what_currency", null, locale));
            switch (enteredData = io.readOneLine().toUpperCase()) {
            case "HUF":
                player.setCurrency(Currency.HUF);
                dataIsValid = true;
                break;
            case "EUR":
                player.setCurrency(Currency.EUR);
                dataIsValid = true;
                break;
            case "USD":
                player.setCurrency(Currency.USD);
                dataIsValid = true;
                break;
            default:
//                io.println("not a valid currency: " + enteredData);
                io.print(messageSource.getMessage("is_not_valid2", new Object[] {enteredData}, locale));

            }

        } while (!dataIsValid);
    }

    private void readBalance(Player player) { // private
        String enteredData;
        boolean dataIsValid = false;
        do {
//            io.println("How much money do you have? (more than 0)");
            io.print(messageSource.getMessage("how_much", null, locale));
            try {
                if (!(enteredData = io.readOneLine()).equals("")) {
                    player.setBalance(Long.parseLong(enteredData));
                    dataIsValid = true;
                }
            } catch (NumberFormatException e1) {
//                io.println("Invalid amount");
                io.print(messageSource.getMessage("invalid_amount", null, locale));
            }
        } while (!dataIsValid);
    }

    private void readAccountNumber(Player player) {
        String enteredData;
//        String out = "What is your account number?";
//        io.println(out);
        // io.println("What is your account number?");
        io.print(messageSource.getMessage("what_account", null, locale));
        if (!(enteredData = io.readOneLine()).equals("")) {
            player.setAccountNumber(enteredData);
        }
    }

    private void readName(Player player) {
        String enteredData;
        io.println(messageSource.getMessage("what_is_your_name", null, locale));
        //io.println("What is your name?");
        if (!(enteredData = io.readOneLine()).equals("")) {
            player.setName(enteredData);
        }
    }

    public double readAmount(Player player) {
        double choosedAmount = 0;
        boolean amountIsValid;
        do {
//            io.println("How much do you want to bet on it? (q for quit)");
            io.println(messageSource.getMessage("much_bet", null, locale));
            amountIsValid = false;
            String amount = io.readOneLine();
            if (amount.equals("q")) { //
                return 0;
            }
            try {
                choosedAmount = Double.parseDouble(amount);
                if (choosedAmount <= player.getBalance()) {
                    amountIsValid = true;
                } else {
                    io.print(messageSource.getMessage("dont_have", new Object[] {player.getBalance(), player.getCurrency()}, locale));
//                    io.println("You don't have enough money, your balance is " + player.getBalance() + player.getCurrency());
                }
            } catch (NumberFormatException e) {
//                io.println("not a valid amount: ");
                io.println(messageSource.getMessage("not_valid", null, locale));
            }
        } while (!amountIsValid);
        return choosedAmount;
    }

    public String readOneLine() {
        return io.readOneLine();
    }

}
