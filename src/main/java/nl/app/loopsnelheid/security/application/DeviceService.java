package nl.app.loopsnelheid.security.application;

import nl.app.loopsnelheid.privacy.domain.exception.DeviceNotFoundException;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import nl.app.loopsnelheid.security.data.DeviceRepository;
import nl.app.loopsnelheid.security.domain.Device;
import nl.app.loopsnelheid.security.domain.EDevice;
import nl.app.loopsnelheid.security.domain.EOSDevice;
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

    public Device getDeviceBySession(String session)
    {
        return deviceRepository.findBySession(session)
                .orElseThrow(() -> new DeviceNotFoundException("Uw huidige apparaat is niet gekoppeld aan dit account."));
    }

    public Device createDevice(User authenticatedUser, String deviceOs, String deviceInfo)
    {
        EDevice eDevice = authenticatedUser.getAmountOfDevices() > 0
                ? EDevice.READING_DEVICE
                : EDevice.MEASURING_DEVICE;
        Device device = new Device(
                TokenGenerator.generateToken(),
                deviceInfo,
                eDevice,
                EOSDevice.valueOf(deviceOs),
                authenticatedUser
        );

        return deviceRepository.save(device);
    }

    public void revokeDevice(Device device, User authenticatedUser)
    {
        if (device.getEDevice() == EDevice.MEASURING_DEVICE && authenticatedUser.getAmountOfDevices() > 1)
        {
            Device newDevice = authenticatedUser.getRandomDeviceExceptGivenSession(device.getSession());
            newDevice.setEDevice(EDevice.MEASURING_DEVICE);

            deviceRepository.save(newDevice);
        }

        deviceRepository.delete(device);
    }

    public void markDeviceAsMeasureDevice(Device device, User authenticatedUser)
    {
        Device previousDevice = authenticatedUser.getCurrentMeasureDevice();
        previousDevice.setEDevice(EDevice.READING_DEVICE);
        device.setEDevice(EDevice.MEASURING_DEVICE);

        deviceRepository.save(previousDevice);
        deviceRepository.save(device);
    }
}