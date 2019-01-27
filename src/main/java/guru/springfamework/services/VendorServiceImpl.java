package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static guru.springfamework.controllers.v1.VendorController.API_V1_VENDORS_URL;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;


@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {

        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    private String getVendorUrl(Long id) {
        return API_V1_VENDORS_URL + id;
    }

    @Override
    public VendorDTO getVendorById(Long Id) {

        return vendorRepository.findById(Id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(Id));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO addVendor(VendorDTO vendorDTO) {
        Vendor savedVendor = vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO));
        return saveVendorToDatabase(savedVendor);

    }

    private VendorDTO saveVendorToDatabase(Vendor savedVendor) {
        VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return returnVendorDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor convertedVendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        convertedVendor.setId(id);
        return saveVendorToDatabase(convertedVendor);
    }

    @Override
    public VendorDTO patchVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor returnedVendor = vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if(isNotEmpty(vendorDTO.getName())) {
            returnedVendor.setName(vendorDTO.getName());
        }
        return saveVendorToDatabase(returnedVendor);
    }

    @Override
    public void deleteByVendorId(Long id) {
        vendorRepository.deleteById(id);
    }
}
