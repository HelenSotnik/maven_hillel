package org.hillel.lesson12;

import org.hillel.lesson12.daos.AccountDao;
import org.hillel.lesson12.daos.ClientDao;
import org.hillel.lesson12.daos.StatusDao;
import org.hillel.lesson12.entities.Account;
import org.hillel.lesson12.entities.Client;
import org.hillel.lesson12.entities.ClientsStatuses;
import org.hillel.lesson12.entities.Status;

public class Main {
    public static void main(String[] args) {
        ClientDao clientDao = new ClientDao();
        testClientDao(clientDao);

        AccountDao accountDao = new AccountDao();
        testAccountDao(accountDao);

        StatusDao statusDao = new StatusDao();
        testStatusDao(statusDao);
    }

    private static void testClientDao(ClientDao clientDao) {
        System.out.println(" List of all clients:");
        for (Client clients : clientDao.findAllClients()) {
            System.out.println(clients);
        }

        Client client = new Client();
        client.setName("Larisa");
        client.setEmail("lorik@yahoo.com");
        client.setPhone(30389027369L);
        client.setAbout("This is Larisa");
        client.setAge(17);
        clientDao.saveClient(client);

        clientDao.deleteClient(2);

        Client client1 = clientDao.findById(3);
        System.out.println("Client before update:\n" + client1);
        client1.setPhone(380505276092L);
        clientDao.updateClient(client1);
        System.out.println("Client after update:\n" + client1);

        System.out.println(clientDao.findClientByPhone(380983725142L));

        System.out.println(" List of clients where id equals account's client_id:");
        for (Client clients : clientDao.findClientsWhereIdEqualsAccountsClientId()) {
            System.out.println(clients);
        }

        System.out.println(" List of clients name,email,alias with age>18:");
        for (ClientsStatuses cs : clientDao.findNameEmailAlias()) {
            System.out.println(cs);
        }
    }

    private static void testAccountDao(AccountDao accountDao) {
        System.out.println(" List of accounts:");
        for (Account accounts : accountDao.findAllAccounts()) {
            System.out.println(accounts);
        }

        Account account = new Account();
        account.setClient_id(35);
        account.setNumber("5765 2345 6709 8843");
        account.setValue(400.00);
        accountDao.saveAccount(account);

        accountDao.deleteAccount(11);

        Account account1 = accountDao.findById(3);
        System.out.println("Account before update:\n" + account1);
        account1.setNumber("8877 9186 2789 0003");
        accountDao.updateAccount(account1);
        System.out.println("Account after update:\n" + account1);

        accountDao.findAccountNumberBiggerThanGivenValue(1000.00);
    }

    private static void testStatusDao(StatusDao statusDao) {
        System.out.println(" List of statuses:");
        for (Status statuses : statusDao.findAllStatuses()) {
            System.out.println(statuses);
        }

        Status status = new Status();
        status.setAlias("PREMIUM");
        status.setDescription("PREMIUM status includes special manager service. ");
        statusDao.saveStatus(status);

        statusDao.deleteStatus(4);

        Status status2 = statusDao.findById(2);
        System.out.println("Status before update:\n" + status2);
        status2.setDescription("Premium suggests premium service.");
        statusDao.updateStatus(status2);
        System.out.println("Status after update:\n" + status2);
    }
}
