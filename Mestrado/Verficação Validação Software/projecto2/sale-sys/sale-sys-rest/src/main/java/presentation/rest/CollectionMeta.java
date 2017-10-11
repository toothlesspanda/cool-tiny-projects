package presentation.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionMeta {

    @SuppressWarnings("unused") private int pageSize;
    @SuppressWarnings("unused") private String nextPage;
    @SuppressWarnings("unused") private String previousPage;
    @SuppressWarnings("unused") private int currentPageNumber;
    @SuppressWarnings("unused") private int totalCount;

    public CollectionMeta() {
    }

    public CollectionMeta(int pageSize, String nextPage, String previousPage,
            int currentPageNumer, int totalCount) {
        this.pageSize = pageSize;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
        this.currentPageNumber = currentPageNumer;
        this.totalCount = totalCount;
    }

}
