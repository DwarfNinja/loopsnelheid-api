package nl.app.loopsnelheid.security.application;

import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import nl.app.loopsnelheid.security.data.DeviceRepository;
import nl.app.loopsnelheid.security.domain.Device;
import nl.app.loopsnelheid.security.domain.EDevice;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeviceService
{
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository)
    {
        this.deviceRepository = deviceRepository;
    }


    public Device createDevice(User authenticatedUser)
    {
        EDevice eDevice = authenticatedUser.getAmountOfDevices() > 0
                ? EDevice.READING_DEVICE
                : EDevice.MEASURING_DEVICE;
        Device device = new Device(TokenGenerator.generateToken(), eDevice, authenticatedUser);

        return deviceRepository.save(device);
    }


}