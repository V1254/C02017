package CO2017.exercise2.masf1;

/**
 * Concrete Class to represent a MemManager using a worst fit allocation policy.
 *
 * @author (masf1) 179029141
 */
public class WorstFitMemManager extends MemManager {


    /**
     * Construct a WorstFitMemManager instance.
     *
     * @param s - the total size of memory to be created
     */
    public WorstFitMemManager(int s) {
        super(s);
    }

    /**
     * Find an address space large enough for s using the worst fit strategy
     *
     * @param s the size of memory that needs to be allocated.
     * @return the address of a space of at least size s.
     */

    @Override
    protected int findSpace(int s) {

//        // details about the worst fit so far.
//        int address = -1;
//        int currentSize = 0;
//
//        // counter to go through the _memory
//        int counter = 0;
//
//        while (counter < _memory.length) {
//
//            int availableSize = countFreeSpacesAt(counter);
//
//            // see if space is at least enough to fit s.
//            if (availableSize >= s) {
//
//                // update the address the first time some space is found that can hold s.
//                if (address < 0) {
//                    address = counter;
//                }
//
//                // worst fit.
//                if (currentSize < availableSize) {
//                    address = counter;
//                    currentSize = availableSize;
//                }
//
//            }
//
//            counter += currentSize > 1 ? currentSize : 1;
//        }

        return 0;


    }


}
