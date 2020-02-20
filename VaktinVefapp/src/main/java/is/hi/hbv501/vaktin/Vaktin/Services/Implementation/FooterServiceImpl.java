package is.hi.hbv501.vaktin.Vaktin.Services.Implementation;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;
import is.hi.hbv501.vaktin.Vaktin.Repositories.FooterRepository;
import is.hi.hbv501.vaktin.Vaktin.Services.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Service implementation for Footer
 *
 * Has private method validateNumbers
 */
@Service
public class FooterServiceImpl implements FooterService {

    FooterRepository footerRepository;

    @Autowired
    public FooterServiceImpl(FooterRepository footerRepository) {
        this.footerRepository = footerRepository;
    }

    @Override
    public Footer save(Footer footer) {
        return this.footerRepository.save(footer);
    }

    @Override
    public void delete(Footer footer) {
        this.footerRepository.delete(footer);
    }

    @Override
    public Footer findByDate(LocalDate date) {
        return this.footerRepository.findByDate(date);
    }

    /***
     * Method validateNumbers
     *
     * Checks if phone numbers are numerical
     * @param footer
     * @return
     */
    private boolean validateNumbers(Footer footer) {
        Pattern p = Pattern.compile("^\\+?(([0-9]*)-?)*$");
        Matcher matchHeadDoctorNumber = p.matcher(String.valueOf(footer.getHeadDoctorNumber()));
        Matcher matchShiftManagerNumber = p.matcher(String.valueOf(footer.getShiftManagerNumber()));
        boolean boolHeadDoctorNumber = matchHeadDoctorNumber.matches();
        boolean boolShiftManagerNumber = matchShiftManagerNumber.matches();
        System.out.println("boolHeadDoctorNubmer " + boolHeadDoctorNumber);
        System.out.println("boolShiftManagerNubmer " + boolShiftManagerNumber);
        return boolHeadDoctorNumber && boolShiftManagerNumber;
    }

    /***
     * Method validate
     * Validates Footer from html form
     * @param footer
     * @return
     */
    @Override
    public boolean validate(Footer footer) {
        if (!validateNumbers(footer)) {
            return false;
        }

        if (footer.getHeadDoctorNumber().equals("")) {
            footer.setHeadDoctorNumber("0");
        }

        if (footer.getShiftManagerNumber().equals("")) {
            footer.setShiftManagerNumber("0");
        }

        Footer exists =  findByDate(LocalDate.now());
        if (exists != null) {
            exists.setHeadDoctor(footer.getHeadDoctor());
            exists.setHeadDoctorNumber(footer.getHeadDoctorNumber());
            exists.setShiftManager(footer.getShiftManager());
            exists.setShiftManagerNumber(footer.getShiftManagerNumber());
            save(exists);
            return true;
        }

        footer.setDate(LocalDate.now());
        save(footer);

        return true;
    }
}
