package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Company;
import mk.ukim.finki.wp.proekt.repository.CompanyRepository;
import mk.ukim.finki.wp.proekt.sevice.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(String name, String address, String description) {
        if(!name.isEmpty()){
            Company company=new Company(name, address, description);
            return this.companyRepository.save(company);
        }else {
            //TODO:exception
            return null;
        }
    }

    @Override
    public Company findById(Integer id) {
        if(this.companyRepository.findById(id).isPresent()){
            return this.companyRepository.findById(id).get();
        }
        else{
            //TODO: exception
            return null;
        }
    }
}
