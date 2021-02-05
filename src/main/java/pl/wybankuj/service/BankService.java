package pl.wybankuj.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wybankuj.entity.Bank;
import pl.wybankuj.repository.BankRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class BankService {

    private final BankRepository bankRepository;
    private final HttpServletRequest request;

    public BankService(BankRepository bankRepository, HttpServletRequest request) {
        this.bankRepository = bankRepository;
        this.request = request;
    }

    public Iterable<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    public Optional<Bank> findBankById(Long bankId) {
        return bankRepository.findById(bankId);
    }

    public void addBankWithLogo(MultipartFile file, Bank bank) throws IOException {
        String logoName = "logo" + bank.getBankName() + ".jpg";
        File fileToUpload = makePath(logoName);
        file.transferTo(fileToUpload);
        Bank bankToSave = new Bank(bank.getBankName(), logoName);
        bankRepository.save(bankToSave);
    }

    // pod React!
    public Bank addBank(Bank bank) {
        Bank bankToSave = new Bank(bank.getBankName(), bank.getLogo());
        return bankRepository.save(bankToSave);
    }

    public void editBank(MultipartFile file, Bank bank, String logo) throws IOException {
        if (file.isEmpty()) {
            String logoToSet = "logo" + bank.getBankName() + ".jpg";
            bank.setLogo(logoToSet);
            bankRepository.save(bank);
            File actualFile = makePath(logo);
            File fileToChange = makePath(logoToSet);
            actualFile.renameTo(fileToChange);
            return;
        }
        File fileToDelete = makePath(logo);
        fileToDelete.delete();
        String logoName = "logo" + bank.getBankName() + ".jpg";
        File fileToUpload = makePath(logoName);
        file.transferTo(fileToUpload);
        bank.setLogo(logoName);
        bankRepository.save(bank);
    }

    // pod React
    public Bank editBank2(Bank bank) {
        return bankRepository.save(bank);
    }

    public void deleteBank(Long bankId) {
        bankRepository.deleteById(bankId);
    }

    public void deleteLogo(String logo) {
        File fileToDelete = makePath(logo);
        fileToDelete.delete();
    }

    public File makePath(String logoName) {
        String filePath = request.getSession().getServletContext().getRealPath("/img");
        return new File(filePath + "/" + logoName);
    }
}
