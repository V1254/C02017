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

        // address of the essentially the biggest space
        int address = -1;
        // size of that space from the address.
        int currentWorstSize = 0;

        // loop for the while counter.
        int counter = 0;

        while(counter < _memory.length){

            int availableSize = countFreeSpacesAt(counter);

            if(availableSize >= s){

                // updating counter & currentWorstSize first time an available address is found.
                if(address < 0){
                    address = counter;
                    currentWorstSize = availableSize;
                }

                // comparing current worst size to new available size found and adjusting accordingly.
                if(availableSize > currentWorstSize){
                    currentWorstSize = availableSize;
                    address = counter;
                }
            }

            counter += availableSize > 1 ? availableSize : 1;
        }

        return address;
    }


}
