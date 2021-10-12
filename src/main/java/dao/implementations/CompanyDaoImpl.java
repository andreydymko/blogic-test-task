package dao.implementations;

import dao.entities.Company;
import dao.interfaces.CompanyDao;

import javax.ejb.Stateless;

@Stateless
public class CompanyDaoImpl extends CrudDaoImpl<Company> implements CompanyDao {

    @Override
    protected Class<Company> setClassType() {
        return Company.class;
    }
}
