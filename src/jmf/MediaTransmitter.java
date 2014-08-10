/*
 * Copyright (C) 2014 Nivrito
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jmf;

import javax.media.*;
import javax.media.control.*;
import javax.media.protocol.*;
import javax.media.format.*;

import java.io.IOException;
import java.io.File;

/**
 * This class transmits media data in RTP audio format to a destination.
 *
 * @author Nivrito
 * @version 1.00
 */
public class MediaTransmitter {
    private MediaLocator mediaLoc = null;
    private DataSink Data_Sink = null;
    private Processor Media_Proc = null;
    private static final Format[] DATA_FORMATS = new Format[]{new AudioFormat(AudioFormat.GSM_RTP)};
    private static final ContentDescriptor CONTENT_DESCRIPTOR = new ContentDescriptor(ContentDescriptor.RAW_RTP);
    
    
    /**
     * General constructor, uses media locator to locate source.
     * @param locator MediaLocator for source.
     */
    public MediaTransmitter(MediaLocator locator) {
        mediaLoc = locator;
    }
    
    /**
     * Starts transmission
     * @throws IOException 
     */
    public void startTransmition() throws IOException {
        Media_Proc.start();;
        Data_Sink.open();
        Data_Sink.start();
    }
    
    /**
     * Stops transmission
     * @throws IOException 
     */
    public void stopTransmition() throws IOException  {
        Data_Sink.stop();
        Data_Sink.close();
        Media_Proc.stop();
        Media_Proc.close();
    }
    
    /**
     * Sets data sink for the transmission using a data source.
     * @param ds DataSource parameter
     * @throws IOException
     * @throws NoProcessorException
     * @throws CannotRealizeException
     * @throws NoDataSinkException 
     */
    public void dataSourceSetup(DataSource ds) throws IOException, NoProcessorException, CannotRealizeException, NoDataSinkException  {
        Media_Proc = Manager.createRealizedProcessor(new ProcessorModel(ds, DATA_FORMATS, CONTENT_DESCRIPTOR));
        Data_Sink = Manager.createDataSink(Media_Proc.getDataOutput(), mediaLoc);
        
    }
    
    
    
}
