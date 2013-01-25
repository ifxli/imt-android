package its.my.time;

import its.my.time.data.bdd.DatabaseHandler;
import its.my.time.data.bdd.compte.CompteBean;
import its.my.time.data.bdd.compte.CompteRepository;
import its.my.time.data.bdd.events.eventBase.EventBaseBean;
import its.my.time.data.bdd.events.eventBase.EventBaseRepository;
import its.my.time.data.bdd.utilisateur.UtilisateurBean;
import its.my.time.data.bdd.utilisateur.UtilisateurRepository;
import its.my.time.util.ActivityUtil;
import its.my.time.util.ContactsUtil;
import its.my.time.util.PreferencesUtil;
import its.my.time.util.Types;

import java.util.Calendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentProviderResult;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	private Button btnConnexion;
	private TextView btnInscription;
	private TextView btnMdpLost;
	private EditText pseudo;
	private EditText mdp;
	private OnClickListener clickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		setTheme(android.R.style.Theme_Black_NoTitleBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		this.clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == SplashActivity.this.btnConnexion) {
					connexion();
				} else if (v == SplashActivity.this.btnInscription) {
					ActivityUtil.startProfilActivity(SplashActivity.this);
				}
			}
		};

		this.btnConnexion = (Button) findViewById(R.id.splash_btnConnexion);
		this.btnConnexion.setOnClickListener(this.clickListener);

		this.btnInscription = (TextView) findViewById(R.id.splash_signIn);
		this.btnInscription.setOnClickListener(this.clickListener);

		this.btnMdpLost = (TextView) findViewById(R.id.splash_mdpLost);
		this.btnMdpLost.setOnClickListener(this.clickListener);

		this.pseudo = (EditText) findViewById(R.id.splash_login);
		this.mdp = (EditText) findViewById(R.id.splash_mdp);

		temp();
	}

	private void temp() {
		deleteDatabase(DatabaseHandler.DATABASE_NAME);
		UtilisateurRepository userRepo = new
				UtilisateurRepository(SplashActivity.this); 
		UtilisateurBean user = new UtilisateurBean(); user.setAdresse("42 rue du charpenet");
		user.setCodePostal("69890"); user.setMail("ad.hugon@gmail.com");
		user.setNom("Hugon"); user.setPays("France");
		user.setPrenom("Adrien"); user.setTel("0617454462");
		user.setVille("La Tour de salvagny");
		user.setPseudo("");
		user.setMdp("");
		long resUser = userRepo.insertUtilisateur(user);
		PreferencesUtil.setCurrentUid(SplashActivity.this, resUser);

		CompteRepository repoCompte = new CompteRepository(SplashActivity.this); 
		CompteBean compte = new CompteBean(); 
		compte.setColor(Color.RED); 
		compte.setShowed(true);
		compte.setTitle(Types.Comptes.MYTIME.label); 
		compte.setType(Types.Comptes.MYTIME.id);
		compte.setUid(resUser); 
		long resCompte1 =  repoCompte.insertCompte(compte);

		compte = new CompteBean(); compte.setColor(Color.BLUE);
		compte.setShowed(true); 
		compte.setTitle("Compte 2");
		compte.setType(2); 
		compte.setUid(resUser); 
		long resCompte2 = repoCompte.insertCompte(compte);



		EventBaseRepository adapter = new EventBaseRepository(SplashActivity.this); 
		EventBaseBean bean;
		Calendar calDeb2; 
		Calendar calFin2; 
		bean = new EventBaseBean();
		bean.setCid(resCompte1); 
		bean.setTitle("Voicin un �v�nement");
		calDeb2 = Calendar.getInstance(); 
		bean.sethDeb(calDeb2); 
		calFin2 = Calendar.getInstance(); 
		calFin2.add(Calendar.HOUR, 2);
		bean.sethFin(calFin2);
		bean.setTypeId(Types.Event.MEETING);
		bean.setDetailsId(0); 
		long res = adapter.insertEvent(bean);

		bean.setTitle("Deuxi�me"); 
		res =adapter.insertEvent(bean);

		bean.setTitle("Et un petit dernier..."); 
		bean.setId(2); 
		res = adapter.insertEvent(bean);



		bean.setCid(resCompte2);
		bean.setTitle("Voicin un �v�nement"); 
		calDeb2 = Calendar.getInstance(); 
		calDeb2.add(Calendar.DAY_OF_MONTH, -10);
		bean.sethDeb(calDeb2); 
		calFin2 = (Calendar) calDeb2.clone();
		calFin2.add(Calendar.HOUR, 2); 
		bean.sethFin(calFin2);
		bean.setTypeId(Types.Event.CALL);
		bean.setDetailsId(0); 
		res = adapter.insertEvent(bean);

		bean.setTitle("Deuxi�me"); 
		res = adapter.insertEvent(bean); 
		bean.setTitle("Troisieme...");
		bean.setId(5); 
		res = adapter.insertEvent(bean);
		bean.setTitle("Un autre");
		bean.setId(6); 
		res = adapter.insertEvent(bean);
		bean.setTitle("Et le dernier!!!!!!!!!!"); 
		bean.setId(7); 
		res = adapter.insertEvent(bean);
	}

	private void connexion() {
		if (PreferencesUtil.getCurrentUid(SplashActivity.this) != -1) {
			new LoadMainActivity().execute();
		} else {
			UtilisateurBean user = new UtilisateurBean();
			final UtilisateurRepository connexion = new UtilisateurRepository(
					SplashActivity.this);
			user = connexion.getConnexion(this.pseudo.getText().toString(),
					this.mdp.getText().toString());
			if (user != null) {
				PreferencesUtil.setCurrentUid(this,user.getId());
				starAnimation();
				new LoadMainActivity().execute();
			} else {
				Toast.makeText(this, "Erreur de Connexion !", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void starAnimation() {
		Animation animClock = new RotateAnimation(0f, 35f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animClock.setFillBefore(true);
		animClock.setDuration(2000);
		animClock.setFillAfter(true);

		btnConnexion.setEnabled(false);
		btnInscription.setEnabled(false);
		btnMdpLost.setEnabled(false);
		pseudo.setEnabled(false);
		mdp.setEnabled(false);
		findViewById(R.id.splash_foreground).startAnimation(animClock);
	}

	private class LoadMainActivity extends AsyncTask<Void, Void, Void> {
		private boolean accountCreated;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			starAnimation();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				/*Account account = new Account("a", getString(R.string.ACCOUNT_TYPE));
				AccountManager am = AccountManager.get(SplashActivity.this);
				accountCreated = am.addAccountExplicitly(account, "a", null);
				
				ContentProviderResult[] results = ContactsUtil.addContact(SplashActivity.this, account, "Adrien Hugon");
				
				ContactsUtil.testUpdate(SplashActivity.this, account);*/
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			finish();
			ActivityUtil.startCalendarActivity(SplashActivity.this);
		}
	}
}
