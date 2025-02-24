import java.util.List;

import com.offnine.carten.modal.Deal;

public interface DealService  {
    List<Deal> getDeals();
    Deal createDeal(Deal deal);
    Deal updateDeal(Deal deal,Long id);
    void deleteDeal(Long id) throws Exception;
    
}
