package de.tz.demo.validation;

//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
//import org.passay.dictionary.WordListDictionary;
//import org.passay.dictionary.WordLists;
//import org.passay.dictionary.sort.ArraysSort;
//import org.passay.DictionaryRule;
import org.passay.DigitCharacterRule;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.LowercaseCharacterRule;

import com.google.common.base.Joiner;



public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
    
    	List<Rule> myRules = new Vector<>();
    	
    	/** kein vernünftiges Dictionary vorhanden atm
    	DictionaryRule dictionaryrule;
    	try {
    		dictionaryrule = new DictionaryRule(
    				new WordListDictionary(WordLists.createFromReader(
    						new FileReader[] {new FileReader("words.txt")},
    						false,
    						new ArraysSort())));
    		myRules.add(dictionaryrule);
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}*/
        myRules.add(new LengthRule(8, 30));
        myRules.add(new UppercaseCharacterRule(1));
        myRules.add(new LowercaseCharacterRule(1));
        myRules.add(new DigitCharacterRule(1));
        myRules.add(new SpecialCharacterRule(2));
        myRules.add(new WhitespaceRule());
        
        
        final PasswordValidator validator = new PasswordValidator(myRules);
            
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }

}
