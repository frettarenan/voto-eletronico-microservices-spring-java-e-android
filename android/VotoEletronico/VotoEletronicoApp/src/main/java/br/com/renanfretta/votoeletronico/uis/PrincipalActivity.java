package br.com.renanfretta.votoeletronico.uis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.dtos.UsuarioLogadoDTO;
import br.com.renanfretta.votoeletronico.utils.SessaoUsuarioUtil;

public class PrincipalActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private PrincipalActivity currentActivity;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private View headerView;

    private Button btSair;
    private TextView tvNomeUsuario;
    private TextView tvLoginUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);
        currentActivity = this;

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio,
                R.id.nav_pauta,
                R.id.nav_sessao_votacao
        ).setOpenableLayout(drawer).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        btSair = (Button) headerView.findViewById(R.id.nav_header_main_bt_sair);
        tvNomeUsuario = (TextView) headerView.findViewById(R.id.nav_header_main_tv_nome_usuario);
        tvLoginUsuario = (TextView) headerView.findViewById(R.id.nav_header_main_tv_login_usuario);

        UsuarioLogadoDTO usuarioLogadoDTO = SessaoUsuarioUtil.getInstance(currentActivity).getUsuarioLogado();
        tvNomeUsuario.setText(usuarioLogadoDTO.getNome());
        tvLoginUsuario.setText(usuarioLogadoDTO.getLogin());

        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessaoUsuarioUtil.getInstance(currentActivity).logout();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

}