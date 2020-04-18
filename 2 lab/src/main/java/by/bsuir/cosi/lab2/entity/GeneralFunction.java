package by.bsuir.cosi.lab2.entity;

public class GeneralFunction {

    private double minX;
    private double maxX;
    private double step;

    public GeneralFunction(double minX, double maxX, double step) {
        this.minX = minX;
        this.maxX = maxX;
        this.step = step;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralFunction that = (GeneralFunction) o;

        if (Double.compare(that.minX, minX) != 0) return false;
        if (Double.compare(that.maxX, maxX) != 0) return false;
        return Double.compare(that.step, step) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(minX);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(step);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "GeneralFunction{" +
                "minX=" + minX +
                ", maxX=" + maxX +
                ", step=" + step +
                '}';
    }
}
