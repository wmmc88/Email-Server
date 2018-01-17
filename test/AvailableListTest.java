import org.junit.jupiter.api.Test;


/**
 * Created by Melvin Wang on 2017-03-01.
 */
class AvailableListTest {

    @Test
    void main(){
        AvailableList availableList = new AvailableList();

        availableList.addRecord(18);
        availableList.addRecord(3);
        availableList.addRecord(64);
        availableList.addRecord(23);

        System.out.println(availableList);

        System.out.println(availableList.getNextRecord());

        System.out.println(availableList);
    }

}