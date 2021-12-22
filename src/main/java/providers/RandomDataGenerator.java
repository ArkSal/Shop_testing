package providers;

public class RandomDataGenerator {

    public int getRandomNumberInRangeMinMax(int minNumber, int maxNumber) {
        return (int) (Math.random() * ((maxNumber - minNumber) + 1) + minNumber);
    }

    public int getRandomNumberInRange(int maxValue) {
        return (int) (Math.random() * (maxValue + 1));
    }
}
