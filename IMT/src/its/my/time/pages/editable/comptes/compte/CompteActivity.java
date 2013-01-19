package its.my.time.pages.editable.comptes.compte;

import its.my.time.data.bdd.compte.CompteBean;
import its.my.time.pages.editable.BaseActivity;
import its.my.time.util.ActivityUtil;
import its.my.time.util.DatabaseUtil;
import android.os.Bundle;

import com.actionbarsherlock.R;

public class CompteActivity extends BaseActivity {

	private CompteBean compte;

	@Override
	protected void onCreate(Bundle savedInstance) {
		setContentView(R.layout.activity_compte);

		Bundle bundle = getIntent().getExtras();
		if(bundle.getLong(ActivityUtil.KEY_EXTRA_ID) >= 0) {
			compte = DatabaseUtil.getCompteRepository(this).getById(bundle.getLong(ActivityUtil.KEY_EXTRA_ID)); 
		} 
		if(compte == null) {
			compte = new CompteBean();
		}
	
		super.onCreate(bundle);
	}
	
	@Override
	protected CharSequence getActionBarTitle() {
		return "Compte";
	}

	@Override
	protected void showEdit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void showSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void showCancel() {
		// TODO Auto-generated method stub
		
	}

}