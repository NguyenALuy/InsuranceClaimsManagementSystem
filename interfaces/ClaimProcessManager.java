package interfaces;

import model.Claim;

import java.util.ArrayList;

public interface ClaimProcessManager {
    Claim addNewClaim();
    boolean updateClaimById(String claimId);
    boolean deleteClaimById(String claimId);
    Claim getOneClaim(String claimId);
    void getAllClaims();
}
