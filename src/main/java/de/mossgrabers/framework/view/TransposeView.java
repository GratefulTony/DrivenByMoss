// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2018
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.framework.view;

import de.mossgrabers.framework.utils.ButtonEvent;


/**
 * Interface for transposing up and down.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public interface TransposeView
{
    /**
     * Trigger to display 1 octave lower.
     *
     * @param event The button event
     */
    void onOctaveDown (final ButtonEvent event);


    /**
     * Trigger to display 1 octave higher.
     *
     * @param event The button event
     */
    void onOctaveUp (final ButtonEvent event);
}
