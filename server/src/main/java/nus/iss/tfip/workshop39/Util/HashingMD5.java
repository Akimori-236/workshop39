package nus.iss.tfip.workshop39.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Hex;

// WEIRD thing is.. need outdated dependencies
//  <!-- https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime -->
//  <dependency>
// 		<groupId>org.glassfish.jaxb</groupId>
// 		<artifactId>jaxb-runtime</artifactId>
// 		<version>2.3.3</version>
// 	</dependency>
// 	<!-- https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api -->
// 	<dependency>
// 		<groupId>jakarta.xml.bind</groupId>
// 		<artifactId>jakarta.xml.bind-api</artifactId>
// 		<version>2.3.3</version>
// 	</dependency>
public class HashingMD5 {

    public static String generateMarvelHash(int timestamp, String privateKey, String publicKey)
            throws NoSuchAlgorithmException {
        // GET HASHING ALGO
        MessageDigest md = MessageDigest.getInstance("MD5");
        // THING TO FEED IN
        String password = timestamp + privateKey + publicKey;
        // NOM NOM
        md.update(password.getBytes());
        // DIGEST
        byte[] digest = md.digest();
        // SPIT OUT
        String hash = DatatypeConverter.printHexBinary(digest);
        return hash.toLowerCase();
    }

    // NEED THIS
    // <!-- https://mvnrepository.com/artifact/commons-codec/commons-codec -->
    // <dependency>
    // <groupId>commons-codec</groupId>
    // <artifactId>commons-codec</artifactId>
    // <version>1.15</version>
    // </dependency>
    public static String generateMarvelHashNEW(int timestamp, String privateKey, String publicKey)
            throws NoSuchAlgorithmException {
        // GET HASHING ALGO
        MessageDigest md = MessageDigest.getInstance("MD5");
        // THING TO FEED IN
        String password = timestamp + privateKey + publicKey;
        // NOM NOM
        md.update(password.getBytes());
        // DIGEST
        byte[] digest = md.digest();
        // SPIT OUT
        String hash = Hex.encodeHexString(digest);
        return hash.toLowerCase();
    }

}
