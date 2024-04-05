/**
 * @author <Nguyen A Luy - S3891919>
 */
package interfaces;
import model.Claim;

public interface ClaimProcessManager {
    Claim addNewClaim();
    boolean updateClaimById(String claimId);
    boolean deleteClaimById(String claimId);
    Claim getOneClaim(String claimId);
    void getAllClaims();
}
