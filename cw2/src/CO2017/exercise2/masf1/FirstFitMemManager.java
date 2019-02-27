package CO2017.exercise2.masf1;

/**
 * Concrete Class to represent a MemManager using a first fit allocation policy.
 */
public class FirstFitMemManager extends MemManager {

    /**
     * Construct a FirstFitMemManager instance.
     *
     * @param s the total size of memory to be created
     */
    public FirstFitMemManager(int s) {
        super(s);
    }

    /**
     * Find an address space large enough for s using the first fit strategy
     *
     * @param s the size of memory that needs to be allocated.
     * @return the address of a space of at least size s.
     */
    @Override
    protected int findSpace(int s) {

        // counter to loop through _memory
        int counter = 0;

        while (counter < _memory.length) {

            // available size from the counter position
            int availableSize = countFreeSpacesAt(counter);

            // ensure the availSize is at least large enough to fit s
            if (availableSize >= s) {
                return counter;
            }

            // jump counter to the next position after all the availableSpace.
            counter += availableSize > 1 ? availableSize : 1;
        }

        // only reach here if no space was found to fit s.
        return -1;
    }

}
