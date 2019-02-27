package CO2017.exercise2.masf1;

/**
 * Class to represent a Process.
 *
 * @author (masf1) 179029141
 */
public class Process implements Runnable {

    /**
     * A single char as the ID of the process
     */
    private char ID;

    /**
     * An integer value representing the size of the process in terms of how much memory it needs to be allocated.
     */

    private int size;

    /**
     * An integer value representing the length of runtime for the process.
     */
    private int runTime;

    /**
     * An integer value representing the memory address that has been allocated to this process (should be set to -1 when the address is undefined, ie, when no address is allocated to the process MemManager)
     */
    private int Address;

    /**
     * The MemManager that is in use by the process
     */
    MemManager memManager;

    /**
     * Sets up a Process instance
     *
     * @param m The MemManager that will be used by this process
     * @param i The ID of this process (a single char)
     * @param s The amount of memory needed by the process
     * @param r The runtime that this process will use
     */
    public Process(MemManager m, char i, int s, int r) {
        this.memManager = m;
        this.ID = i;
        this.size = s;
        this.runTime = r;
        this.Address = -1;
    }

    /**
     * Accessor for size attribute.
     *
     * @return the size of the process
     */
    public int getSize() {
        return size;
    }

    /**
     * Accessor for ID attribute.
     *
     * @return the ID character of the process
     */
    public char getID() {
        return ID;
    }

    /**
     * Set the memory address used by this Process
     *
     * @param address address of memory used by this process
     */
    public void setAddress(int address) {
        Address = address;
    }

    /**
     * Accessor for the Address
     *
     * @return the address of the memory used by this process
     */
    public int getAddress() {
        return Address;
    }

    /**
     * Basic behaviour when a Process thread is started.
     */
    @Override
    public void run() {
        System.out.println(this + " waiting to run.");

        try {
            memManager.allocate(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(this + " running.");

        try {
            Thread.sleep(100 * this.runTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memManager.free(this);

        System.out.println(this + " has finished.");

    }

    /**
     * Generate a string representing the Process.
     *
     * @return a string representing the Process
     */
    @Override
    public String toString() {
        String address = Address == -1 ? "U" : String.valueOf(Address);
        return String.format("%c:%3s+%2d", ID, address, size);
    }
}
