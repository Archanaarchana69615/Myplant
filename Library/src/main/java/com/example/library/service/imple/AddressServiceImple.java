package com.example.library.service.imple;

import com.example.library.dto.AddressDto;
import com.example.library.model.Address;
import com.example.library.model.Customer;
import com.example.library.repository.AddressRepository;
import com.example.library.service.AddressService;
import com.example.library.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImple implements AddressService {

    private CustomerService customerService;

    private AddressRepository addressRepository;

    public AddressServiceImple(CustomerService customerService, AddressRepository addressRepository) {
        this.customerService = customerService;
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(AddressDto addressDto, String username) {
        Customer customer= customerService.findByEmail(username);

        Address address = new Address();

        address.setAddressline1(addressDto.getAddressline1());
        address.setAddressline2(addressDto.getAddressline2());
        address.setCity(addressDto.getCity());
        address.setDistrict(addressDto.getDistrict());
        address.setCountry(addressDto.getCountry());
        address.setState(addressDto.getState());
        address.setPincode(addressDto.getPincode());
        address.setCustomer(customer);
        address.set_default(false);
        return addressRepository.save(address);
    }

    @Override
    public AddressDto findById(long id) {

        Address address= addressRepository.findById(id);
        AddressDto addressDto= new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCountry(address.getCountry());
        addressDto.setDistrict(address.getDistrict());
        addressDto.setCity(address.getCity());
        addressDto.setPincode(address.getPincode());
        addressDto.setAddressline1(address.getAddressline1());
        addressDto.setAddressline2(address.getAddressline2());

        return addressDto;
    }

    @Override
    public Address update(AddressDto addressDto, long id) {
        Address address = addressRepository.findById(id);

        Customer customer = address.getCustomer();
        address.setAddressline1(addressDto.getAddressline1());
        address.setAddressline2(addressDto.getAddressline2());
        address.setCountry(addressDto.getCountry());
        address.setPincode(addressDto.getPincode());
        address.setCity(addressDto.getCity());
        address.setDistrict(addressDto.getDistrict());
        address.setState(addressDto.getState());
        address.setCustomer(customer);
        address.set_default(false);

        return addressRepository.save(address);
    }
}
