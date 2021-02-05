package pl.wybankuj.service;

import org.springframework.stereotype.Service;
import pl.wybankuj.entity.Mortgage;
import pl.wybankuj.entity.MortgageWithPayment;
import pl.wybankuj.repository.MortgageRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MortgageService {

    private final static int ROUND_SCALE = 2;
    private final static int CALCULATE_SCALE = 10;

    private final MortgageRepository mortgageRepository;

    public MortgageService(MortgageRepository mortgageRepository) {
        this.mortgageRepository = mortgageRepository;
    }

//    public Map<Mortgage, BigDecimal> calculateMortgagePayment(List<Mortgage> mortgages, int amount, int creditPeriod) {
//        Map<Mortgage, BigDecimal> mortgagesWithPayments = new HashMap<>();
//
//        for (Mortgage mortgage : mortgages) {
//            mortgagesWithPayments.put(mortgage, calculateChoosenMortgagePayment(mortgage, amount, creditPeriod));
//        }
//        mortgagesWithPayments = sortByPayment(mortgagesWithPayments);
//        return mortgagesWithPayments;
//    }
public List<MortgageWithPayment> calculateMortgagePayment(List<Mortgage> mortgages, int amount, int creditPeriod) {
    List<MortgageWithPayment> mortgagesWithPayments = new ArrayList<>();

    for (Mortgage mortgage : mortgages) {
        MortgageWithPayment mortgageWithPayment = new MortgageWithPayment(mortgage, mortgage.getBank().getId(), mortgage.getBank().getLogo(), calculateChoosenMortgagePayment(mortgage, amount, creditPeriod));
        mortgagesWithPayments.add(mortgageWithPayment);
    }
    mortgagesWithPayments.sort(Comparator.comparing(MortgageWithPayment::getPayment));

    return mortgagesWithPayments;
}

    public BigDecimal calculateChoosenMortgagePayment(Mortgage mortgage, int amount, int creditPeriod) {
        BigDecimal rateRatio = BigDecimal.valueOf(1).add((mortgage.getCreditRate().divide(BigDecimal.valueOf(100), CALCULATE_SCALE, RoundingMode.CEILING)).divide(BigDecimal.valueOf(12), CALCULATE_SCALE, RoundingMode.CEILING));
        BigDecimal totalAmount = BigDecimal.valueOf(amount).add(BigDecimal.valueOf(amount).multiply(mortgage.getServiceCharge().divide(BigDecimal.valueOf(100), 10, RoundingMode.CEILING))).add(BigDecimal.valueOf(amount).multiply(mortgage.getInsurance().divide(BigDecimal.valueOf(100), 10, RoundingMode.CEILING)));
        BigDecimal payment = totalAmount.multiply(rateRatio.pow(creditPeriod)).multiply((rateRatio.subtract(BigDecimal.valueOf(1))).divide((rateRatio.pow(creditPeriod)).subtract(BigDecimal.valueOf(1)), CALCULATE_SCALE, RoundingMode.CEILING));
        return payment.setScale(ROUND_SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateServiceCharge(Mortgage mortgage, int amount) {
        BigDecimal serviceCharge = BigDecimal.valueOf(amount).multiply(mortgage.getServiceCharge().divide(BigDecimal.valueOf(100), CALCULATE_SCALE, RoundingMode.CEILING));
        return serviceCharge.setScale(ROUND_SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateInsurance(Mortgage mortgage, int amount) {
        BigDecimal insurance = BigDecimal.valueOf(amount).multiply(mortgage.getInsurance().divide(BigDecimal.valueOf(100), CALCULATE_SCALE, RoundingMode.CEILING));
        return insurance.setScale(ROUND_SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateInterestsCost(Mortgage mortgage, int amount, int creditPeriod, BigDecimal payment, BigDecimal serviceCharge, BigDecimal insurance) {
        BigDecimal interests = BigDecimal.valueOf(creditPeriod).multiply(payment).subtract(BigDecimal.valueOf(amount)).subtract(serviceCharge).subtract(insurance);
        return interests.setScale(ROUND_SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalCost(Mortgage mortgage, int amount, int creditPeriod, BigDecimal payment) {
        BigDecimal interests = BigDecimal.valueOf(creditPeriod).multiply(payment).subtract(BigDecimal.valueOf(amount));
        return interests.setScale(ROUND_SCALE, RoundingMode.HALF_UP);
    }

    private Map<Mortgage, BigDecimal> sortByPayment(Map<Mortgage, BigDecimal> mapToSort) {
        Map<Mortgage, BigDecimal> sortedMap =
                mapToSort.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }

    public List<Mortgage> chooseOffers(String chooseInsurance, String chooseServiceCharge,
                                       int amount, int creditPeriod, int age, BigDecimal contributionPercent) {
        List<Mortgage> mortgages = new ArrayList<>();
        if (chooseInsurance.equals("no") && chooseServiceCharge.equals("no")) {
            mortgages = mortgageRepository.findAllByParametersWithoutInsuranceAndServiceCharge(amount, creditPeriod, age, contributionPercent);
        } else if (chooseInsurance.equals("no") && chooseServiceCharge.equals("yes")) {
            mortgages = mortgageRepository.findAllByParametersWithoutInsurance(amount, creditPeriod, age, contributionPercent);
        } else if (chooseServiceCharge.equals("no") && chooseInsurance.equals("yes")) {
            mortgages = mortgageRepository.findAllByParametersWithoutServiceCharge(amount, creditPeriod, age, contributionPercent);
        } else {
            mortgages = mortgageRepository.findAllByParameters(amount, creditPeriod, age, contributionPercent);
        }
        return mortgages;
    }
}
