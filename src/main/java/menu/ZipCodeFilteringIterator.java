package menu;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class ZipCodeFilteringIterator<T> implements Iterator<T> {
    private final List<T> dataList;
    private final String targetZipCode;
    private final Function<T, String> zipCodeExtractor;
    private int currentIndex;

    public ZipCodeFilteringIterator(List<T> dataList, String targetZipCode, Function<T, String> zipCodeExtractor) {
        this.dataList = dataList;
        this.targetZipCode = targetZipCode;
        this.zipCodeExtractor = zipCodeExtractor;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        while (currentIndex < dataList.size()) {
            T item = dataList.get(currentIndex);
            if (item != null) {
                String zipCode = zipCodeExtractor.apply(item);
                if (zipCode != null && zipCode.equals(targetZipCode)) {
                    return true;
                }
            }
            currentIndex++;
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements matching zip code: " + targetZipCode);
        }
        return dataList.get(currentIndex++);
    }
}

