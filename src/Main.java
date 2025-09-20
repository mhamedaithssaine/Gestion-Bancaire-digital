
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import Module.Transaction;

import RepositoriesMemoire.InMemoryAccountRepository;
import RepositoriesMemoire.InMemoryUserRepository;
import RepositoriesMemoire.InMemoryTransactionRepository;
import Services.AccountService;
import Services.AuthService;
import Services.TransactionService;
import Module.User;
import Module.Account;
import Services.TransactionService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService(new InMemoryUserRepository());
        User currentUser = null ;
        while(true){
            if (currentUser == null){

           System.out.println("_________Accueil__________");
            System.out.println("1. Register");
            System.out.println("2. login");
            System.out.println("3. Exit");
            System.out.print("Choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Full Name  : ");
                    String fullName = scanner.nextLine();
                    System.out.print("Email  : ");
                    String email = scanner.nextLine();
                    System.out.print("Address : ");
                    String address = scanner.nextLine();
                    System.out.print("Password : ");
                    String password = scanner.nextLine();
                    String register = authService.register(fullName, email, address, password);
                    System.out.println(register);
                    break;
                case 2:
                    System.out.print("Email : ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Password : ");
                    String loginPassword = scanner.nextLine();
                    User loginUser = authService.login(loginEmail, loginPassword);
                    if (loginUser != null) {
                        currentUser = loginUser;
                        System.out.println(" Logged in successfully. Welcome " + loginUser.getFullName());
                    } else {
                        System.out.println("Invalid Credentials");
                    }

                    break;
                case 3:
                    System.out.print("By ");

                    scanner.close();
                    return;
                default:
                    System.out.println("Wrong choice ");
             }

            }  else {
                currentUser = userMenu( scanner,  currentUser, authService);

            }
        }

    }


   private static User userMenu (Scanner scanner , User currentUser, AuthService authService){
       AccountService accountService = new AccountService(new InMemoryAccountRepository(),  new TransactionService(new InMemoryTransactionRepository()));

       while(true){
             System.out.println("______ User Menu("+currentUser.getFullName()+")______");
             System.out.println("1.  create account ");
             System.out.println("2.  My list account");
             System.out.println("3.  Deposit ");
             System.out.println("4.  Withdraw ");
             System.out.println("5.  Transfer");
             System.out.println("6. Transaction History");
             System.out.println("7.  Update profile");
             System.out.println("8.  Change password");
             System.out.println("9.  Close account");
             System.out.println("10.  Logout ");


             System.out.print("Choix : ");
             int choice = scanner.nextInt();
             scanner.nextLine();
             switch (choice){

                 case 1:
                     if(currentUser != null){
                         Account newAccount = accountService.createdAccount(currentUser.getId());
                         System.out.println("Account created :"+newAccount.getAccountId()+"| Ballence :"+ newAccount.getBalance());
                     }
                     else System.out.println("You are not login !!");

                     break;
                 case 2:
                     List<Account> myAccounts = accountService.getMyAccounts(currentUser.getId());
                     if (myAccounts.isEmpty()) {
                         System.out.println("No account found !");
                     } else {
                         for (Account acc : myAccounts) {
                             System.out.println("Account ID: " + acc.getAccountId() +
                                     " | Balance: " + acc.getBalance());
                             if(acc.isActive()){
                                 System.out.println("Account active .");
                             } else System.out.println("Account inActive");

                         }
                     }
                     break;
                 case 3:
                     myAccounts = accountService.getMyAccounts((currentUser.getId()));
                     if(myAccounts.isEmpty()){
                         System.out.println("You don't have an account");
                         break;
                     }
                     System.out.println("Your accounts:");

                     for (Account acc : myAccounts) {
                         System.out.println(" - " + acc.getAccountId() + " | Balance: " + acc.getBalance());
                     }
                     System.out.println("Enter Account id to deposit : ");
                     String accountId = scanner.nextLine();
                     System.out.println("Enter amount to deposit: ");
                     BigDecimal amount = scanner.nextBigDecimal();

                     Account amountDeposit = accountService.deposit(accountId,currentUser.getId(),amount);
                     if(amountDeposit != null ){
                         System.out.println("Amount to deposit succefully : " + amountDeposit.getBalance());
                         break;
                     }
                     break;
                 case 4:
                      myAccounts = accountService.getMyAccounts(currentUser.getId());
                      if(myAccounts.isEmpty()){
                          System.out.println("You don't have an account");
                          break;
                      }
                      System.out.println("Your accounts :");
                      for (Account acc : myAccounts) {
                          System.out.println(" - " + acc.getAccountId() + " | Balance :" + acc.getBalance());
                      }
                         System.out.println("Enter Account id to deposit : ");
                         String accountIdInput  = scanner.next();
                         System.out.println("Enter amount to withdrawAmount : ");
                         BigDecimal withdrawAmount = scanner.nextBigDecimal();
                         Account withdraw = accountService.withdraw(accountIdInput,currentUser.getId(),withdrawAmount);
                         if(withdraw != null){
                             System.out.println("Withdrawal successful! New balance: " + withdraw.getBalance());
                         }else {
                             System.out.println("Withdrawal failed. Please check your account ID or balance !");
                         }

                     break;
                 case 5:
                     myAccounts = accountService.getMyAccounts(currentUser.getId());
                     if(myAccounts.isEmpty()){
                         System.out.println("You don't have any account!");
                         break;
                     }
                     System.out.println("Your accounts:");
                     for (Account acc : myAccounts) {
                         System.out.println(" - " + acc.getAccountId() + " | Balance: " + acc.getBalance());
                     }

                     System.out.println("Enter source account ID: ");
                     String fromAccId = scanner.nextLine();
                     System.out.println("Enter destination account ID: ");
                     String toAccId = scanner.nextLine();
                     System.out.println("Enter amount to transfer: ");
                     BigDecimal transferAmount = scanner.nextBigDecimal();

                     Account updatedSource = accountService.transfer(fromAccId, toAccId, currentUser.getId(), transferAmount);
                     if(updatedSource != null){
                         System.out.println("Transfer successful! New balance: " + updatedSource.getBalance());
                     } else {
                         System.out.println("Transfer failed!");
                     }
                     break;

                 case 6:
                     myAccounts = accountService.getMyAccounts(currentUser.getId());
                     if(myAccounts.isEmpty()){
                         System.out.println("You don't have an account!");
                         break;
                     }
                     System.out.println("Your accounts:");
                     for (Account acc : myAccounts) {
                         System.out.println(" - " + acc.getAccountId());
                     }
                     System.out.println("Enter account ID to view history: ");
                     String histAccId = scanner.nextLine();

                     List<Transaction> history = accountService.getTransactionHistory(histAccId, currentUser.getId());
                     if(history == null || history.isEmpty()){
                         System.out.println("No transactions found!");
                     } else {
                         for(Transaction t : history){
                             System.out.println(t.getTimeTamps()+" | "+t.getType()+" | "+t.getAmount()+" | "+t.getDescription());
                         }
                     }
                     break;

                 case 7:
                     System.out.print("New full name : ");
                     String newFullName = scanner.nextLine();
                     System.out.print("New email     : ");
                     String newEmail = scanner.nextLine();
                     System.out.print("New address   : ");
                     String newAddress = scanner.nextLine();
                     User updatedUser = authService.updateProfile(currentUser, newFullName, newEmail, newAddress);
                     if (updatedUser != null) {
                         currentUser = updatedUser;
                         System.out.println("Profile updated successfully.");
                     } else {
                         System.out.println("Failed to update profile !");
                     }
                     break;
                 case 8:
                         System.out.println("change password");
                         System.out.println("New password : ");
                         String newPassword = scanner.next();
                         User updateUserPassword = authService.updatePassword(currentUser,newPassword);
                         if (updateUserPassword !=null ){
                             currentUser.setPassword(updateUserPassword.getPassword());
                             System.out.println("Password updated successfully. ");
                         } else {
                             System.out.print("Fialed to update Password !");
                         }
                         break;
                 case 9:

                     myAccounts = accountService.getMyAccounts(currentUser.getId());
                     if (myAccounts.isEmpty()) {
                         System.out.println("You don’t have any account!");
                         break;
                     }
                     for (Account acc : myAccounts) {
                         System.out.println(" - " + acc.getAccountId() );
                         if(acc.isActive()){
                             System.out.println("Account active .");
                         } else System.out.println("Account inActive");
                     }
                     System.out.print("Enter account ID to close: ");
                     String accIdToClose = scanner.nextLine();
                     Account closed = accountService.closeAccount(accIdToClose, currentUser.getId());
                     if (closed != null) {
                         System.out.println(" Account " + closed.getAccountId() + " closed successfully!");
                     } else {
                         System.out.println("Failed to close account.");
                     }
                     break;


                 case 10:
                             System.out.println("logout successful");
                             currentUser = null;
                             return null ;


                                 default:
                                     System.out.println("Wrong choice ");

             }
         }
   }
}