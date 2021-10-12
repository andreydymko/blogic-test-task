package services.implementations;

import dao.entities.Company;
import dao.interfaces.CompanyDao;
import dao.interfaces.CrudDao;
import services.interfaces.CompanyService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

@Stateless
@Transactional(Transactional.TxType.REQUIRED)
public class CompanyServiceImpl extends CrudServiceImpl<Company> implements CompanyService {
    @EJB
    private CompanyDao companyDao;

    @Override
    protected CrudDao<Company> setDAO() {
        return companyDao;
    }

    @Override
    public void update(Long oldItemId, Company newItem) {
        newItem.setId(oldItemId);
        super.update(oldItemId, newItem);
    }
}
