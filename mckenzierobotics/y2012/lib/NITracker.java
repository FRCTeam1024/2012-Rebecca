/**
 * Copyright (c) FIRST 2012. All Rights Reserved. Open Source Software - may be
 * modified and shared by FRC teams. The code must be accompanied by the FIRST
 * BSD license file in the root directory of the project.
 */
package org.mckenzierobotics.y2012.lib;

import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 * Sample program to use NIVision to find rectangles in the scene that are
 * illuminated by a red ring light (similar to the model from FIRSTChoice). The
 * camera sensitivity is set very low so as to only show light sources and
 * remove any distracting parts of the image.
 *
 * The CriteriaCollection is the set of criteria that is used to filter the set
 * of rectangles that are detected. In this example we're looking for rectangles
 * with a minimum width of 30 pixels and maximum of 400 pixels. Similar for
 * height (see the addCriteria() methods below.
 *
 * The algorithm first does a color threshold operation that only takes objects
 * in the scene that have a significant red color component. Then removes small
 * objects that might be caused by red reflection scattered from other parts of
 * the scene. Then a convex hull operation fills all the rectangle outlines
 * (even the partially occluded ones). Finally a particle filter looks for all
 * the shapes that meet the requirements specified in the criteria collection.
 */
public class NITracker {

	CriteriaCollection cc; // the criteria for doing the particle filter operation

	public NITracker() {
		cc = new CriteriaCollection(); // create the criteria for the particle filter
		cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
		cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
	}

	public ParticleAnalysisReport[] processImage(ColorImage image) throws NIVisionException {
		BinaryImage thresholdImage = image.thresholdRGB(25, 255, 0, 45, 0, 47);    // keep only red objects
		BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2); // remove small artifacts
		BinaryImage convexHullImage = bigObjectsImage.convexHull(false);           // fill in occluded rectangles
		BinaryImage filteredImage = convexHullImage.particleFilter(cc);            // find filled in rectangles

		ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results

		/* all images in Java must be freed after they are used since they are
		 * allocated out of C data structures. Not calling free() will cause the
		 * memory to accumulate over each pass.
		 */
		filteredImage.free();
		convexHullImage.free();
		bigObjectsImage.free();
		thresholdImage.free();

		return reports;
	}
}
