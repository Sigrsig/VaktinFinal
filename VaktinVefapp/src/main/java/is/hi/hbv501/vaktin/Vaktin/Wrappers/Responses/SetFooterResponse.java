package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;

import java.util.List;

public class SetFooterResponse extends GenericResponse {

    Footer footer;

    public SetFooterResponse(Footer footer) {
        this.footer = footer;
    }

    public SetFooterResponse(String message, List<?> errors, Footer footer) {
        super(message, errors);
        this.footer = footer;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
}
