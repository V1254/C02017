package CO2017.exercise2.masf1;

import java.util.Arrays;

/**
 * Abstract class MemManager representing the current state of memory and methods to grant access to it.
 *
 * @author Mohamed Abdulwahid Sharif-Farah (masf1) 179029141
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
     * @param s the total size of memory to be created
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
     * @param s the size of memory that needs to be allocated.
     * @return the memory address of a suitable space of at least size s.
     */
    protected abstract int findSpace(int s);


    /**
     * Start at address pos and calculate the size of the contiguous empty space begining there.
     *
     * @param pos start address
     * @return the number of free memory cells, starting at the given address
     */
    int countFreeSpacesAt(int pos) {

        int space = 0;

        while (true) {
            // guard for if at the end of the memory
            if (pos >= _memory.length) {
                return space;
            }

            // empty space is any position with the character '.'
            if (_memory[pos] == '.') {
                space++;
                pos++;
            } else {
                return space;
            }
        }
    }

    /**
     * Allocate memory for a process
     *
     * @param p The Process to allocate memory for.
     * @throws InterruptedException via wait.
     */

    public synchronized void allocate(Process p) throws InterruptedException {

        // block until space is available
        while (p.getSize() > _largestSpace) wait();

        // address of free space.
        int freeAddress = findSpace(p.getSize());

        p.setAddress(freeAddress);

        for (int i = freeAddress; i < freeAddress + p.getSize(); i++) {
            _memory[i] = p.getID();
        }

        // Re-caculate the value of the _largestSpace.
        reCalculateLargestSpace();

        // changed
        _changed = true;

        // notify the squad
        notifyAll();

    }

    /**
     * Free memory used by a process.
     *
     * @param p Process to de-allocate memory for
     */
    public synchronized void free(Process p) {

        // Reset the contents of the memory array used by the process so that it contains '.' again.
        for (int i = p.getAddress(); i < (p.getAddress() + p.getSize()); i++) {
            _memory[i] = '.';
        }

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

            // end the row
            sb.append("|");
            // start a new line for the next row.
            sb.append("\n");
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
    private int reCalculateLargestSpace() {
        _largestSpace = 0;
        int start = 0;
        while (start < _memory.length) {
            //size of space at curr
            int thisPass = countFreeSpacesAt(start);
            _largestSpace = Math.max(_largestSpace, thisPass);
            //jump to the address after the free block beginning at curr
            start += Math.max(1, thisPass);
        }
        return _largestSpace;
    }


}
