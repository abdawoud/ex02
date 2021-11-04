package saarland.cispa.trust.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import saarland.cispa.trust.serviceapp.utils.NetworkService;

public class RemoteService extends Service {

    private NetworkService networkService;

    public RemoteService() {
        networkService = new NetworkService();
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public String getVersion() throws RemoteException {
            String allowedPackage = "saarland.cispa.trust.cispabay";
            int callerUid = Binder.getCallingUid();
            String callingApp = getPackageManager().getNameForUid(callerUid);
            if (callingApp.equals(allowedPackage))
                return networkService.getServiceVersion();
            else
                throw new SecurityException("Not authorized!");
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
