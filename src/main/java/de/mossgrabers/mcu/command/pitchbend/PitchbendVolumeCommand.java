// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2018
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.mcu.command.pitchbend;

import de.mossgrabers.framework.command.core.AbstractPitchbendCommand;
import de.mossgrabers.framework.daw.IChannelBank;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.ITrackBank;
import de.mossgrabers.framework.daw.data.ITrack;
import de.mossgrabers.framework.mode.ModeManager;
import de.mossgrabers.mcu.MCUConfiguration;
import de.mossgrabers.mcu.controller.MCUControlSurface;
import de.mossgrabers.mcu.mode.Modes;


/**
 * Command to handle pitchbend.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class PitchbendVolumeCommand extends AbstractPitchbendCommand<MCUControlSurface, MCUConfiguration>
{
    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     */
    public PitchbendVolumeCommand (final IModel model, final MCUControlSurface surface)
    {
        super (model, surface);
    }


    /** {@inheritDoc} */
    @Override
    public void onPitchbend (final int channel, final int data1, final int data2)
    {
        final double value = Math.min (data2 * 127 + (double) data1, this.model.getValueChanger ().getUpperBound () - 1);
        if (channel == 8)
        {
            this.model.getMasterTrack ().setVolume (value);
            return;
        }

        final int extenderOffset = this.surface.getExtenderOffset ();
        final IChannelBank tb = this.model.getCurrentTrackBank ();
        final ITrack track = tb.getTrack (extenderOffset + channel);
        if (this.surface.getConfiguration ().useFadersAsKnobs ())
        {
            final ModeManager modeManager = this.surface.getModeManager ();
            if (modeManager.isActiveMode (Modes.MODE_VOLUME))
                track.setVolume (value);
            else if (modeManager.isActiveMode (Modes.MODE_PAN))
                track.setPan (value);
            else if (modeManager.isActiveMode (Modes.MODE_TRACK))
                this.handleTrack (channel, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND1))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 0, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND2))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 1, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND3))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 2, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND4))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 3, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND5))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 4, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND6))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 5, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND7))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 6, value);
            else if (modeManager.isActiveMode (Modes.MODE_SEND8))
                ((ITrackBank) tb).setSend (extenderOffset + channel, 7, value);
            else if (modeManager.isActiveMode (Modes.MODE_DEVICE_PARAMS))
                this.model.getCursorDevice ().setParameter (extenderOffset + channel, (int) value);
            return;
        }

        track.setVolume (value);
    }


    private void handleTrack (final int index, final double value)
    {
        final boolean effectTrackBankActive = this.model.isEffectTrackBankActive ();

        final IChannelBank tb = this.model.getCurrentTrackBank ();
        final ITrack selectedTrack = tb.getSelectedTrack ();

        switch (index)
        {
            case 0:
                selectedTrack.setVolume (value);
                return;
            case 1:
                selectedTrack.setPan (value);
                return;
        }

        final MCUConfiguration config = this.surface.getConfiguration ();

        if (index == 2)
        {
            if (config.isDisplayCrossfader ())
            {
                final double range = this.model.getValueChanger ().getUpperBound () / 3.0;
                tb.setCrossfadeModeAsNumber (selectedTrack.getIndex (), (int) Math.round (value / range));
            }
            else if (!effectTrackBankActive)
                ((ITrackBank) tb).setSend (selectedTrack.getIndex (), 0, value);
        }
        else if (!effectTrackBankActive)
            ((ITrackBank) tb).setSend (selectedTrack.getIndex (), index - (config.isDisplayCrossfader () ? 3 : 2), value);
    }
}
