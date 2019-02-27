package CO2017.exercise2.masf1;

import java.util.Arrays;

/**
 * Abstract class MemManager representing the current state of memory and methods to grant access to it.
 *
 * @author (masf1) 179029141
 */
public abstract class MemManager {

    /**
     * boolean that records if the state of the memory has changed since the last time toString was invoked
     */
    boolean _changed;

    /**
     * The size of the largest currently free block of memory
     */
    int _largestSpace;

    /**
     * Array representation of the memory
     */
    char[] _memory;

    /**
     * Create a new MemManager of specified size.
     *
     * @param s - the total size of memory to be created
     */

    public MemManager(int s) {
        // Initialize fields and set _changed to true.
        _largestSpace = s;
        _changed = true;
        _memory = new char[s];

        // Initialize the memory contents to an empty value of '.'
        Arrays.fill(_memory, '.');
    }

    /**
     * Return whether the state of memory has changed since the last invocation of toString
     *
     * @return true iff memory has changed state since the last call to toString
     */

    public boolean isChanged() {
        return _changed;
    }

    /**
     * Find an address in memory where s amount of space is available.
     *
     * @param s - the size of memory that needs to be allocated.
     * @return the memory address of a suitable space of at least size s.
     */
    protected abstract int findSpace(int s);


    /**
     * Start at address pos and calculate the size of the contiguous empty space beginning there.
     *
     * @param pos - start address
     * @return the number of free memory cells, starting at the given address
     */
    int countFreeSpacesAt(int pos) {
        int space = 0;
        while (pos < _memory.length) {
            // if the current spot is not empty return the value of space.
            if(_memory[pos] != '.')
                return space;

            // otherwise increment both and continue.
            space++;
            pos++;
        }
        return space;
    }

    /**
     * Allocate memory for a process
     *
     * @param p The Process to allocate memory for.
     * @throws InterruptedException via wait.
     */

    public synchronized void allocate(Process p) throws InterruptedException {

        // block until space is available
        while (p.getSize() > _largestSpace) {
            wait();
        }

        // address of free space.
        int freeAddress = findSpace(p.getSize());

        // assign address to the process
        p.setAddress(freeAddress);

        // fill from freeAddress to freeAddress + size with the processes id.
        Arrays.fill(_memory,freeAddress,(freeAddress+p.getSize()),p.getID());

        // Re-caculate the value of the _largestSpace.
        reCalculateLargestSpace();

        // the state of memory has changed.
        _changed = true;



        // notify any blocked processes that this operation is complete.
        notifyAll();
    }

    /**
     * Free memory used by a process.
     *
     * @param p Process to de-allocate memory for
     */
    public synchronized void free(Process p) {

        // Reset the contents of the memory array used by the process so that it contains '.' again.
        Arrays.fill(_memory,p.getAddress(),(p.getAddress() + p.getSize()),'.');

        // Reset the address allocated to the process to -1.
        p.setAddress(-1);

        // Re-caculate the value of the _largestSpace.
        reCalculateLargestSpace();

        // the state of memory has changed.
        _changed = true;

        // notify any blocked processes that this operation is complete.
        notifyAll();
    }

    /**
     * Generate a string representing the state of the Memory.
     *
     * @return String representing the state of memory
     */
    public String toString() {

        _changed = false;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < _memory.length; i++) {

            // starting a new row
            if (i % 20 == 0) {
                // left pad the string
                String padded = String.format("%3s", String.valueOf(i));
                sb.append(padded);
                sb.append("|");
            }

            sb.append(_memory[i]);

            if (i == _memory.length - 1 || i % 20 == 19) {
                // end the row with pipe
                sb.append("|");
                // start a new line for the next row.
                sb.append("\n");
            }
        }

        // add the final string displayed as ls:
        sb.append(String.format("ls:%3d", _largestSpace));

        return sb.toString();

    }

    /**
     * Re-Calculates the Largest Free Space.
     *
     * @return The largest available free space in _memory.
     */
    private synchronized void reCalculateLargestSpace() {
        this._largestSpace = 0;
        int counter = 0;

        while (counter < _memory.length) {
            //size of space at curr
            int currentSpace = countFreeSpacesAt(counter);
            this._largestSpace = Math.max(this._largestSpace, currentSpace);
            counter += currentSpace > 1 ? currentSpace : 1;
        }
    }


}
