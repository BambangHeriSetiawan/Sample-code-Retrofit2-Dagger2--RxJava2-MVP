package mguell.sample_tmbd.deps;

import javax.inject.Singleton;

import dagger.Component;
import mguell.sample_tmbd.ui.presenter.LandingPresenter;

/**
 * Created by Mauricio on 15/06/2017.
 */

@Singleton
@Component(modules = {AppModule.class, TMBDModule.class})
public interface AppComponent {

    void inject(LandingPresenter target);

}