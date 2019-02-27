package CO2017.exercise2.masf1;

/**
 * Concrete Class to represent a MemManager using a best fit allocation policy.
 *
 * @author (masf1) 179029141
 */
public class BestFitMemManager extends MemManager {

    /**
     * Construct a BestFitMemManager instance.
     *
     * @param s the total size of memory to be created
     */

    public BestFitMemManager(int s) {
        super(s);
    }

    /**
     * Find an address space large enough for s using the best fit strategy
     *
     * @param s the size of memory that needs to be allocated.
     * @return the address of a space of at least size s.
     */
    @Override
    protected int findSpace(int s) {

        int startAddress = -1;
        int currentBestSize = 0;

        // counter to iterate through _memory.length
        int counter = 0;

        while (counter < _memory.length) {

            // use the inherited method to find the largest size of free spaces from the current position.
            int availableSize = countFreeSpacesAt(counter);

            // check availSize is enough to fit s.
            if (availableSize >= s) {

                // update the startAddress the first time an availableSpace is found for s.
                if (startAddress < 0) {
                    startAddress = counter;
                }
                // update the value of size depending on the currentAvailable size.
                // if the availAble size is smaller than the current size and also fits S then it is considered the better size.
                if (availableSize < currentBestSize) {
                    currentBestSize = availableSize;
                    startAddress = counter;
                }
            }

            // jump to the next position after the free spaces or go the next counter.
            counter += 1 > availableSize ? 1 : availableSize;
        }

        // returns -1 if no address is available, else it returns the address found from the loop.
        return startAddress;
    }


}
