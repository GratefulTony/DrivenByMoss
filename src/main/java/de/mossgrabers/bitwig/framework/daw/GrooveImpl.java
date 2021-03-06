// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2018
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.bitwig.framework.daw;

import de.mossgrabers.bitwig.framework.daw.data.ParameterImpl;
import de.mossgrabers.framework.controller.IValueChanger;
import de.mossgrabers.framework.daw.IGroove;
import de.mossgrabers.framework.daw.data.IParameter;

import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.Groove;


/**
 * Encapsulates the Groove instance.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class GrooveImpl implements IGroove
{
    private Groove        groove;
    private IParameter [] parameters = new IParameter [6];


    /**
     * Constructor.
     *
     * @param host The host
     * @param valueChanger The value changer
     */
    public GrooveImpl (final ControllerHost host, final IValueChanger valueChanger)
    {
        this.groove = host.createGroove ();

        final int maxParameterValue = valueChanger.getUpperBound ();
        this.parameters[0] = new ParameterImpl (valueChanger, this.groove.getEnabled (), maxParameterValue);
        this.parameters[1] = new ParameterImpl (valueChanger, this.groove.getShuffleAmount (), maxParameterValue);
        this.parameters[2] = new ParameterImpl (valueChanger, this.groove.getShuffleRate (), maxParameterValue);
        this.parameters[3] = new ParameterImpl (valueChanger, this.groove.getAccentAmount (), maxParameterValue);
        this.parameters[4] = new ParameterImpl (valueChanger, this.groove.getAccentRate (), maxParameterValue);
        this.parameters[5] = new ParameterImpl (valueChanger, this.groove.getAccentPhase (), maxParameterValue);
    }


    /** {@inheritDoc} */
    @Override
    public void enableObservers (final boolean enable)
    {
        for (final IParameter parameter: this.parameters)
            parameter.enableObservers (enable);
    }


    /** {@inheritDoc} */
    @Override
    public IParameter [] getParameters ()
    {
        return this.parameters;
    }


    /** {@inheritDoc} */
    @Override
    public void setIndication (final boolean enable)
    {
        for (final IParameter p: this.parameters)
            p.setIndication (enable);
    }
}