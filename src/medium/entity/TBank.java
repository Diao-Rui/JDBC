package medium.entity;

/**
 * @Author: Diao Rui
 * @className: TBank
 * @Description: TODO
 * @DateTime: 2023/7/26 17:49
 * @version: 1.0
 **/
public class TBank {
    private int id;
    private String account;
    private int money;

    public TBank() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "TBank{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", money=" + money +
                '}';
    }
}
